package com.ligg.apiclient.service;

import com.ligg.common.module.entity.UserEntity;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
public interface ClientAccountService {

    /**
     * 注册
     */
    int register(UserEntity userEntity);

    /**
     * 将注册信息添加到缓存中
     */
    void addRegisterInfoToCache(UserEntity userEntity);

    /**
     * 根据邮件验证码获取保存的信息
     */
    UserEntity getRegisterInfo(String email);

    /**
     * 更新/创建用户信息
     */
    int updateOrInsertUserInfo(UserEntity userEntity,boolean type);
}
