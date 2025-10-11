package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.module.entity.EmailEntity;
import jakarta.validation.constraints.NotNull;

/**
 * @Author LiGG
 * @Time 2025/10/8
 */
public interface EmailService extends IService<EmailEntity> {

    /**
     * 获取邮箱
     *
     * @param email 邮箱
     * @return 邮箱
     */
    EmailEntity getEmail(@NotNull String email);
}
