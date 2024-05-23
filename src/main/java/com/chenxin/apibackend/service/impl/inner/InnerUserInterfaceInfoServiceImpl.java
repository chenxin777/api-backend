package com.chenxin.apibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chenxin.apibackend.common.ErrorCode;
import com.chenxin.apibackend.exception.BusinessException;
import com.chenxin.apibackend.service.UserInterfaceInfoService;
import com.chenxin.apicommon.model.entity.UserInterfaceInfo;
import com.chenxin.apicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/22 22:47
 * @modify
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
