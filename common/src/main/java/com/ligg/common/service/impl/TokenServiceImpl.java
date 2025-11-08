package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ligg.common.constants.Constant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.service.TokenService;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.GetClientIpUtil;
import com.ligg.common.utils.JWTUtil;
import com.ligg.common.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ligg.common.constants.Constant.EXPIRE;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RedisUtil redisUtil;

    private final HttpServletRequest request;

    private final UserService userService;

    /**
     * 生成token
     * 同时更新最后登录时间
     * @param userEntity 用户信息
     * @return token
     */
    @Override
    public String generateToken(UserEntity userEntity) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(UserConstant.USER_ID, userEntity.getUserId());
        userInfo.put(Constant.EMAIL, userEntity.getEmail());
        userInfo.put(UserConstant.USER_IP, GetClientIpUtil.getIp(request));
        userInfo.put(UserConstant.USER_ROLE, userEntity.getRole().toString());

        userService.update(new LambdaUpdateWrapper<UserEntity>()
                .eq(UserEntity::getUserId, userEntity.getUserId())
                .set(UserEntity::getLastLoginTime, LocalDateTime.now()));
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
