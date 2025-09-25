package com.ligg.ecommerceadmin.service;

import com.ligg.entity.UserEntity;
import jakarta.validation.constraints.NotNull;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
public interface UserService {
    /**
     *  注册账号
     * @param account
     * @param password
     */
    void register(@NotNull String account, @NotNull String password);

    /**
     * 根据账号获取用户信息
     */
    UserEntity getUserInfoByAccount(String account);
}
