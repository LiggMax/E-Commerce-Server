/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.RedisUtil;
import com.ligg.common.module.vo.UserInfoVo;
import com.ligg.common.utils.ThreadLocalUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RedisUtil redisUtil;
    private final UserMapper userMapper;
    /**
     * 注册账号
     * @param account 账号
     * @param password 密码
     */
    @Override
    public void register(String account, String password) {

        String encryptPassword = BCryptUtil.encrypt(password);
        String nickName = "user_" + UUID.randomUUID().toString().substring(0, 6);
        LocalDateTime now = LocalDateTime.now();

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(encryptPassword);
        userEntity.setAccount(account);
        userEntity.setNickName(nickName);
        userEntity.setCreateTime(now);
        userEntity.setAvatar("/");
        userMapper.insert(userEntity);
    }

    /**
     * 根据账号获取用户信息
     */
    @Override
    public UserEntity getUserInfoByAccount(String account) {
        return userMapper.selectOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getAccount, account));
    }

    /**
     * 根据用户id获取用户信息
     */
    @Override
    public UserInfoVo getUserInfoById(String userId) {
        UserEntity redisUserInfo = getRedisUserInfo(userId);
        UserInfoVo userInfoVo = new UserInfoVo();

        if (redisUserInfo == null) {
            UserEntity userEntity = userMapper.selectById(userId);
            if (userEntity != null) {
                redisUtil.set(UserConstant.USER_INFO + ":" + userId, userEntity, 1);
                BeanUtils.copyProperties(userEntity, userInfoVo);
            }
            return userInfoVo;
        }

        BeanUtils.copyProperties(redisUserInfo, userInfoVo);
        return userInfoVo;
    }

    /**
     * 扣减用户余额
     */
    @Override
    @Transactional
    public void debit(@NotNull BigDecimal amount) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        UserEntity userEntity = userMapper.selectById(userId);
        BigDecimal accountBalance = userEntity.getAccountBalance();
        if (accountBalance.compareTo(amount) < 0) {
            throw new RuntimeException("余额不足");
        }
        if (!(userMapper.debitBalance(userId, amount) > 0)) {
            throw new RuntimeException("扣减用户余额失败");
        }
    }

    private UserEntity getRedisUserInfo(String userId) {
        return (UserEntity) redisUtil.get(UserConstant.USER_INFO + ":" + userId);
    }
}
