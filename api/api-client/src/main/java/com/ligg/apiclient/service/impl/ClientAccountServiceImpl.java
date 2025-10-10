package com.ligg.apiclient.service.impl;

import com.ligg.apiclient.service.ClientAccountService;
import com.ligg.common.entity.UserEntity;
import com.ligg.common.enums.UserRole;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.utils.BCryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Service
@RequiredArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService {

    final UserMapper userMapper;

    /**
     * 用户注册
     */
    @Override
    public int register(UserEntity userEntity) {
        String nickName = "user_" + UUID.randomUUID().toString().substring(0, 6);
        userEntity.setNickName(nickName);
        userEntity.setRole(UserRole.USER);
        userEntity.setPassword(BCryptUtil.encrypt(userEntity.getPassword()));
        return userMapper.insert(userEntity);
    }
}
