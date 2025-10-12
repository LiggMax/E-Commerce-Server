package com.ligg.apiclient.service.impl;

import com.ligg.apiclient.service.ClientAccountService;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return userMapper.insert(userEntity);
    }
}
