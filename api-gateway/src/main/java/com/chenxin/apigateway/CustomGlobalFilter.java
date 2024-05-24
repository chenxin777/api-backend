package com.chenxin.apigateway;

import com.chenxin.apiclientsdk.utils.SignUtils;
import com.chenxin.apicommon.model.entity.InterfaceInfo;
import com.chenxin.apicommon.model.entity.User;
import com.chenxin.apicommon.service.InnerInterfaceInfoService;
import com.chenxin.apicommon.service.InnerUserInterfaceInfoService;
import com.chenxin.apicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/21 10:54
 * @modify
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    public static final String INTERFACE_HOST = "http://localhost:8123";

    /**
     * @description 全局过滤
     * @author fangchenxin
     * @date 2024/5/21 12:04
     * @param exchange
     * @param chain
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
        //2、请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识: "+ request.getId());
        log.info("请求路径: "+ request.getPath().value());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求源地址: "+ sourceAddress);
        log.info("请求源地址: "+ request.getRemoteAddress());
        log.info("请求方法: "+ request.getMethod());
        log.info("请求参数: "+ request.getQueryParams());
        ServerHttpResponse response = exchange.getResponse();
        //3、黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            handleNoAuth(response);
        }
        //2、用户鉴权（判断ak、sk是否合法）
        HttpHeaders headers = request.getHeaders();
        String body = headers.getFirst("body");

        // 校验accessKey
        String accessKey = headers.getFirst("accessKey");
        User invokeUser = null;
        try {
             invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception ex) {
            log.error("getInvokeUser Error", ex);
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }
        // 校验随机数
        String nonce = headers.getFirst("nonce");
        if (Long.parseLong(nonce) > 10000L) {
            return handleNoAuth(response);
        }
        // 校验过期时间
        String timestamp = headers.getFirst("timestamp");
        Long currentTime = System.currentTimeMillis() / 1000;
        Long FIVE_MINUTES = 5L * 60;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            handleNoAuth(response);
        }
        // 校验secretKey
        String sign = headers.getFirst("sign");
        String serverSign = SignUtils.genSign(body, invokeUser.getSecreteKey());
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }
        //3、请求的模拟接口是否存在？
        // 从数据库中查询模拟接口是否存在，以及请求方法是否匹配、还可以校验请求参数
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception ex) {
            log.error("getInterfaceInfo Error", ex);
        }
        if (interfaceInfo == null) {
            return handleInvokeError(response);
        }
        // 是否还有调用次数
        Long interfaceInfoId = interfaceInfo.getId();
        Long userId = invokeUser.getId();
        if (!innerUserInterfaceInfoService.isValid(interfaceInfoId, userId)) {
            return handleNoAuth(response);
        }
        return handleResponse(exchange, chain, interfaceInfoId, userId);
    }

    /**
     * @description 处理响应
     * @author fangchenxin
     * @date 2024/5/21 20:50
     * @param exchange
     * @param chain
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完模拟接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值写数据 拼接字符串
                            return super.writeWith(
                                fluxBody.map(dataBuffer -> {
                                    // TODO 统计调用次数
                                    try {
                                        innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                    } catch (Exception ex) {
                                        log.error("invokeCount error", ex);
                                    }
                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    // 释放掉内存
                                    DataBufferUtils.release(dataBuffer);
                                    // 构建日志
                                    String data = new String(content, StandardCharsets.UTF_8);
                                    log.info("响应结果：" + data);
                                    return bufferFactory.wrap(content);
                                })
                            );
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置response为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);
        } catch (Exception ex) {
            log.error("网关处理响应异常" + ex);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
