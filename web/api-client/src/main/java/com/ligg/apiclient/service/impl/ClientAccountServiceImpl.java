package com.ligg.apiclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ligg.apiclient.service.ClientAccountService;
import com.ligg.common.constants.Constant;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.utils.RedisUtil;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Service
@RequiredArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService {

    private final UserMapper userMapper;

    private final RedisUtil redisUtil;

    private final static String REGISTER = Constant.REGISTER;

    /**
     * 用户注册
     */
    @Override
    public int register(UserEntity userEntity) {
        int insert = userMapper.insert(userEntity);
        redisUtil.del(REGISTER + userEntity.getEmail());
        return insert;
    }

    /**
     * 将注册信息添加到缓存中
     */
    @Override
    public void addRegisterInfoToCache(UserEntity userEntity) {
        redisUtil.set(REGISTER + userEntity.getEmail(), userEntity, Constant.EMAIL_EXPIRE, TimeUnit.MINUTES);
    }

    /**
     * 根据邮件验证码获取保存的信息
     */
    @Override
    public UserEntity getRegisterInfo(String email) {
        return (UserEntity) redisUtil.get(REGISTER + email);
    }

    /**
     * 根据邮箱更新/创建用户信息
     */
    @Override
    public int updateOrInsertUserInfo(UserEntity userEntity, boolean type) {
        if (type) {
            int insert = userMapper.insert(userEntity);
            redisUtil.del(REGISTER + userEntity.getEmail());
            return insert;
        } else {
            int update = userMapper.updateUserInfoByEmail(userEntity);
            redisUtil.del(REGISTER + userEntity.getEmail());
            return update;
        }
    }
}
