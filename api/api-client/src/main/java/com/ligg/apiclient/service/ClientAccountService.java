package com.ligg.apiclient.service;

import com.ligg.common.entity.UserEntity;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
public interface ClientAccountService {

    /**
     * 注册
     */
    int register(UserEntity userEntity);
}
