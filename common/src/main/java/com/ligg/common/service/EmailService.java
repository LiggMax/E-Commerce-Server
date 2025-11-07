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
     * 发送邮件验证码
     */
    void sendVerificationCode(String toEmail);

    /**
     * 检查是否可以发送验证码（频率限制检查）
     *
     * @param email 邮箱
     * @return true表示可以发送，false表示发送过于频繁
     */
    boolean canSendVerificationCode(String email);
    /**
     * 根据邮箱号获取信息
     *
     * @param email 邮箱
     * @return 邮箱
     */
    EmailEntity getEmail(@NotNull String email);
}
