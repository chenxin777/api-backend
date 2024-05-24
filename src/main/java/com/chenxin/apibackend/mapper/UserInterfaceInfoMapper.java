package com.chenxin.apibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenxin.apicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author fangchenxin
* @description 针对表【user_interface_info(api.`user_interface_info`)】的数据库操作Mapper
* @createDate 2024-05-19 13:04:58
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * @description 接口调用次数排名
     * @author fangchenxin
     * @date 2024/5/24 15:23
     * @param limit
     * @return java.util.List<com.chenxin.apicommon.model.entity.UserInterfaceInfo>
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




