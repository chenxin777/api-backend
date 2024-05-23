package com.chenxin.apibackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
@Data
public class IdRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}