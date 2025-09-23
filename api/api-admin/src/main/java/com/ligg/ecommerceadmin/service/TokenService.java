package com.ligg.ecommerceadmin.service;

import com.ligg.entity.admin.UserEntity;
import com.ligg.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;

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
