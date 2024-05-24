package com.chenxin.apicommon.service;

/**
* @author fangchenxin
* @description 针对表【user_interface_info(api.`user_interface_info`)】的数据库操作Service
* @createDate 2024-05-19 13:04:58
*/
public interface InnerUserInterfaceInfoService {

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
     * @description 判断接口是否可用（调用次数）
     * @author fangchenxin
     * @date 2024/5/24 15:25
     * @param interfaceInfoId
     * @param userId
     * @return boolean
     */
    boolean isValid(long interfaceInfoId, long userId);

}
