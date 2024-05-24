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
        return "POST 用户名字是：" + user.getName();
    }


}
