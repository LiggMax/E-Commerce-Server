package com.ligg.common.service.impl;

import com.ligg.common.entity.UserEntity;
import com.ligg.common.service.TokenService;
import com.ligg.common.utils.JWTUtil;
import com.ligg.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${token.expire}")
    private Long EXPIRE;


    /**
     * 生成token
     * @param userEntity 用户信息
     * @return  token
     */
    @Override
    public String generateToken(UserEntity userEntity) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userEntity.getUserId());
        userInfo.put("account", userEntity.getAccount());
        return jwtUtil.createToken(userInfo);
    }

    /**
     * 保存token
     * @param token token
     * @param userId 用户id
     */
    @Override
    public void saveToken(String token, String userId) {
        redisUtil.set("Token:" + userId, token,EXPIRE);
    }
}
