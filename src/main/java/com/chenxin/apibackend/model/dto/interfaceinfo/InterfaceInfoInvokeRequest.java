package com.chenxin.apibackend.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户请求
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private static final long serialVersionUID = -2576386793977459540L;
    /**
     * id
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;

}