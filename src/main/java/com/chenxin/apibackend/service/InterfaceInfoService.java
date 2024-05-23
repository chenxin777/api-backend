package com.chenxin.apibackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenxin.apibackend.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.chenxin.apibackend.model.vo.InterfaceInfoVO;
import com.chenxin.apicommon.model.entity.InterfaceInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fangchenxin
 * @description 针对表【interface_info(api.`interface_info`)】的数据库操作Service
 * @createDate 2024-05-16 12:08:51
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterface(InterfaceInfo interfaceInfo, boolean add);

    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

    InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);

    Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);
}
