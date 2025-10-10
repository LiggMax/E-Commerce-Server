package com.ligg.common.service.impl;

import com.ligg.common.constants.Constant;
import com.ligg.common.entity.UserEntity;
import com.ligg.common.service.TokenService;
import com.ligg.common.utils.JWTUtil;
import com.ligg.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ligg.common.constants.Constant.EXPIRE;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RedisUtil redisUtil;

    /**
     * 生成token
     *
     * @param userEntity 用户信息
     * @return token
     */
    @Override
    public String generateToken(UserEntity userEntity) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(Constant.USER_ID, userEntity.getUserId());
        userInfo.put(Constant.ACCOUNT, userEntity.getAccount());
        return JWTUtil.createToken(userInfo, EXPIRE);
    }

    /**
     * 保存token到Redis
     *
     * @param token  token
     * @param userId 用户id
     */
    @Override
    public void saveToken(String token, String userId) {
        redisUtil.set(Constant.TOKEN + ':' + userId, token, EXPIRE);
    }

    /**
     * 删除Redis中的token
     *
     * @param userId 用户id
     */
    @Override
    public void deleteRedisToken(String userId) {
        redisUtil.del(Constant.TOKEN + ':' + userId);
    }
}
