package com.chenxin.apibackend.service;

import com.chenxin.apicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceTest {

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    void invoke() {
        boolean res = innerUserInterfaceInfoService.invokeCount(1, 1);
        System.out.println(res);

    }
}