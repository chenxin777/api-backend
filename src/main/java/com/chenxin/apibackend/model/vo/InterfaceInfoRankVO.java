package com.chenxin.apibackend.model.vo;

import com.chenxin.apicommon.model.entity.InterfaceInfo;
import lombok.Data;

/**
 * @author fangchenxin
 * @description
 * @date 2024/5/23 23:57
 * @modify
 */
@Data
public class InterfaceInfoRankVO extends InterfaceInfo {

    private static final long serialVersionUID = -6863380558142535930L;

    /**
     * 调用次数
     */
    private Integer totalNum;




}
