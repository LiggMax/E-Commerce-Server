package com.ligg.common.service.impl;

import com.ligg.common.constants.Constant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.service.TokenService;
import com.ligg.common.utils.GetClientIp;
import com.ligg.common.utils.JWTUtil;
import com.ligg.common.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ligg.common.constants.Constant.EXPIRE;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RedisUtil redisUtil;

    private final HttpServletRequest request;

    /**
     * 生成token
     *
     * @param userEntity 用户信息
     * @return token
     */
    @Override
    public String generateToken(UserEntity userEntity) {
        String userToken = (String) redisUtil.get(Constant.TOKEN + userEntity.getUserId());

        if (StringUtils.hasText(userToken)) {
            return userToken;
        } else {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put(UserConstant.USER_ID, userEntity.getUserId());
            userInfo.put(Constant.ACCOUNT, userEntity.getAccount());
            userInfo.put(UserConstant.USER_IP, GetClientIp.getIp(request));
            userInfo.put(UserConstant.USER_ROLE, userEntity.getRole().toString());
            return JWTUtil.createToken(userInfo, EXPIRE);
        }
    }

    /**
     * 保存token到Redis
     *
     * @param token  token
     * @param userId 用户id
     */
    @Override
    public void saveToken(String token, String userId) {
        redisUtil.set(Constant.TOKEN + userId, token, EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 删除Redis中的token
     *
     * @param userId 用户id
     */
    @Override
    public void deleteRedisToken(String userId) {
        redisUtil.del(Constant.TOKEN + userId);
    }
}
