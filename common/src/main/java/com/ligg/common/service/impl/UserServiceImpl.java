/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.common.constants.Constant;
import com.ligg.common.entity.UserEntity;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.RedisUtil;
import com.ligg.common.vo.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final RedisUtil redisUtil;
    final UserMapper userMapper;
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
                redisUtil.set(Constant.USER_INFO + ":" + userId, userEntity, 3);
                BeanUtils.copyProperties(userEntity, userInfoVo);
            }
            return userInfoVo;
        }

        BeanUtils.copyProperties(redisUserInfo, userInfoVo);
        return userInfoVo;
    }

    private UserEntity getRedisUserInfo(String userId) {
        return (UserEntity) redisUtil.get(Constant.USER_INFO + ":" + userId);
    }
}
