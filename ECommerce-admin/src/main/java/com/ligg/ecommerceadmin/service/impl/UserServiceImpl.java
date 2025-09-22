package com.ligg.ecommerceadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.ecommerceadmin.mapper.UserMapper;
import com.ligg.ecommerceadmin.service.UserService;
import com.ligg.entity.admin.UserEntity;
import com.ligg.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册账号
     * @param account
     * @param password
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
}
