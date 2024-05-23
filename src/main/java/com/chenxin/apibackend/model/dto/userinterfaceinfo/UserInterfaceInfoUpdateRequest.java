package com.chenxin.apibackend.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    private static final long serialVersionUID = -8726234906740560277L;

    private Long id;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 接口状态 0正常1禁用
     */
    private Integer status;

}