package com.chenxin.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.chenxin.apiclientsdk.model.User;
import com.chenxin.apiclientsdk.utils.SignUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fangchenxin
 * @description 调用第三方客户端
 * @date 2024/5/17 12:19
 * @modify
 */
public class ApiClient {

    public static final String GATEWAY_HOST = "http://localhost:8090";

    private String accessKey;

    private String secretKey;

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("name", name);
        String res = HttpUtil.get(GATEWAY_HOST + "/api/name/get", paraMap);
        System.out.println(res);
        return res;
    }

    public String getNameByPost( String name) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("name", name);
        String res = HttpUtil.post(GATEWAY_HOST + "/api/name/post", paraMap);
        System.out.println(res);
        return res;
    }

    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .charset(StandardCharsets.UTF_8)
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String body = httpResponse.body();
        System.out.println(body);
        return body;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        // 不能直接发送
        //headerMap.put("secretKey", secretKey);
        headerMap.put("nonce", RandomUtil.randomNumbers(4));
        headerMap.put("body", body);
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headerMap.put("sign", SignUtils.genSign(body, secretKey));
        return headerMap;
    }


}
