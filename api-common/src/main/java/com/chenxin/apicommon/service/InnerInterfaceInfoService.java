package com.chenxin.apicommon.service;

import com.chenxin.apicommon.model.entity.InterfaceInfo;


/**
 * @author fangchenxin
 * @description 针对表【interface_info(api.`interface_info`)】的数据库操作Service
 * @createDate 2024-05-16 12:08:51
 */
public interface InnerInterfaceInfoService {

    /**
     * @description 查询接口信息
     * @author fangchenxin
     * @date 2024/5/22 20:42
     * @param path
     * @param method
     * @return model.entity.InterfaceInfo
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

}
