package com.ligg.common.service;

import com.ligg.common.module.entity.UserEntity;

public interface TokenService {

    /**
     * 生成token
     * 同时更新最后登录时间
     */
    String generateToken(UserEntity userEntity);

    /**
     * 储存 token
     */
    void saveToken(String token, String userId);

    /**
     * 删除Redis中的token
     */
    void deleteRedisToken(String userId);

}
