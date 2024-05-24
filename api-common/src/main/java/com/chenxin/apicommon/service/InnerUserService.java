package com.chenxin.apicommon.service;

import com.chenxin.apicommon.model.entity.User;

/**
 * 用户服务
 *
 * @author <a href="https://github.com/lichenxin">程序员鱼皮</a>
 * @from <a href="https://chenxin.icu">编程导航知识星球</a>
 */
public interface InnerUserService {

    /**
     * @description 库中查分配给用户的密钥是否存在
     * @author fangchenxin
     * @date 2024/5/22 20:37
     * @param accessKey
     * @return model.entity.User
     */
    User getInvokeUser(String accessKey);

}
