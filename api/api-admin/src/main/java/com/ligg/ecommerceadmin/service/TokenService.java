package com.ligg.ecommerceadmin.service;

import com.ligg.entity.UserEntity;

public interface TokenService {

    /**
     * 生成token
     */
    String generateToken(UserEntity userEntity);

    /**
     * 储存 token
     */
    void saveToken(String token, String userId);
}
