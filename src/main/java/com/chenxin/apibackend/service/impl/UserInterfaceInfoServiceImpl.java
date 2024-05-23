package com.chenxin.apibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenxin.apibackend.common.ErrorCode;
import com.chenxin.apibackend.exception.BusinessException;
import com.chenxin.apibackend.mapper.UserInterfaceInfoMapper;
import com.chenxin.apibackend.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.chenxin.apibackend.service.UserInterfaceInfoService;
import com.chenxin.apicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author fangchenxin
* @description 针对表【user_interface_info(api.`user_interface_info`)】的数据库操作Service实现
* @createDate 2024-05-19 13:04:58
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterface(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }

    @Override
    public QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (userInterfaceInfoQueryRequest == null) {
            return queryWrapper;
        }
        return queryWrapper;
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {

        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("leftNum = leftNum + 1, totalNum = totalNum - 1");
        return this.update(updateWrapper);
    }

}




