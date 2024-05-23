package com.chenxin.apiinterface.controller;


import com.chenxin.apiclientsdk.model.User;
import com.chenxin.apiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/17 11:53
 * @modify
 */
@RestController()
@RequestMapping("/name")
public class NameController {

    @RequestMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request) {
        return "Get 你的名字是：" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "Post 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        // 实际去数据库中查ak是否分配给用户
        if (!accessKey.equals("chenxin")) {
            throw new RuntimeException("无权限");
        }
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        // 时间和当前时间不能超过5分钟
        if (Math.abs(Long.parseLong(timestamp) - (System.currentTimeMillis() / 1000)) / 60 > 5) {
            throw new RuntimeException("无权限");
        }
        // 实际从数据库中查出secretKey
        String serverSign = SignUtils.genSign(body, "77788");
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("无权限");
        }

        return "POST 用户名字是：" + user.getName();
    }


}
