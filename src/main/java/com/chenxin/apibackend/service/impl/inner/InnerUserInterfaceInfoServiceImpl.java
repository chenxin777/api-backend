package com.chenxin.apibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chenxin.apibackend.common.ErrorCode;
import com.chenxin.apibackend.exception.BusinessException;
import com.chenxin.apibackend.mapper.UserInterfaceInfoMapper;
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

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    /**
     * @description 判断接口是否允许被调用
     * @author fangchenxin
     * @date 2024/5/24 15:36
     * @param interfaceInfoId
     * @param userId
     * @return boolean
     */
    @Override
    public boolean isValid(long interfaceInfoId, long userId) {
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        queryWrapper.eq("userId", userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        if (userInterfaceInfo == null) {
            return false;
        }
        if (userInterfaceInfo.getLeftNum() > 0) {
            return true;
        }
        return false;
    }
}
