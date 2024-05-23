package com.chenxin.apiinterface;

import com.chenxin.apiclientsdk.client.ApiClient;
import com.chenxin.apiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ApiInterfaceApplicationTests {

    @Resource
    private ApiClient apiClient;

    @Test
    void contextLoads() {
        String fcx = apiClient.getNameByGet("fcx");
        System.out.println("Get:" + fcx);
        User user = new User();
        user.setName("fcx");
        String name = apiClient.getUserNameByPost(user);
        System.out.println("POST: " + name);
    }

}
