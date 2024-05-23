package com.chenxin.apibackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenxin.apibackend.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.chenxin.apicommon.model.entity.InterfaceInfo;
import com.chenxin.apicommon.model.entity.User;
import com.chenxin.apicommon.model.entity.UserInterfaceInfo;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/22 22:52
 * @modify
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * @description 根据请求参数获取sql查询条件
     * @author fangchenxin
     * @date 2024/5/22 23:14
     * @param userInterfaceInfoQueryRequest
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.chenxin.apicommon.model.entity.UserInterfaceInfo>
     */
    QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

    /**
     * @description 调用接口的统计
     * @author fangchenxin
     * @date 2024/5/19 20:46
     * @param interfaceInfoId
     * @param userId
     * @return int
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * @description
     * @author fangchenxin
     * @date 2024/5/22 20:58
     * @param userInterfaceInfo
     * @param add
     */
    void validUserInterface(UserInterfaceInfo userInterfaceInfo, boolean add);

}
