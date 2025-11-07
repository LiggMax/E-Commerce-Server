/**
 * @Author LiGG
 * @Time 2025/10/8
 */
package com.ligg.apicommon.controller;

import com.ligg.common.module.entity.EmailEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.service.EmailService;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 邮箱接口
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    private final UserService userService;

    /**
     * 保存邮箱
     */
    @PostMapping
    public Response<String> saveEmail(@NotNull @Email String email) {

        EmailEntity emailEntity = emailService.getEmail(email);
        if (emailEntity != null) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "该邮箱已经被登记,请使用未登记的邮箱订阅");
        }
        EmailEntity entity = new EmailEntity();
        entity.setEmail(email);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdateAt(LocalDateTime.now());
        emailService.save(entity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 获取邮件验证码
     */
    @Operation(summary = "发送邮件验证码")
    @GetMapping("/send_code")
    public Response<String> sendEmailCode(@NotNull @Email String toEmail) {
        if (userService.lambdaQuery().eq(UserEntity::getEmail, toEmail).exists()) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "该邮箱已经被使用,请更换邮箱");
        }

        if (!emailService.canSendVerificationCode(toEmail)) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "请勿重复发送验证码");
        }
        /*
         * 发送验证码，发送邮箱是异步执行不可以直接捕获异常给前端进行反馈
         */
        emailService.sendVerificationCode(toEmail);
        return Response.success(BusinessStates.SUCCESS, "验证码已发送");
    }
}
