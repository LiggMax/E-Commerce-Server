package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.module.entity.EmailEntity;
import com.ligg.common.mapper.EmailMapper;
import com.ligg.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author LiGG
 * @Time 2025/10/8
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl extends ServiceImpl<EmailMapper, EmailEntity> implements EmailService {

    private final EmailMapper emailMapper;

    /**
     * 获取邮箱
     *
     * @param email 邮箱
     * @return 邮箱
     */
    @Override
    public EmailEntity getEmail(String email) {
        return emailMapper.selectOne(new LambdaQueryWrapper<EmailEntity>()
                .eq(EmailEntity::getEmail, email));
    }
}
