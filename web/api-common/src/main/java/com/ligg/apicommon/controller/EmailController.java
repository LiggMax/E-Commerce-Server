/**
 * @Author LiGG
 * @Time 2025/10/8
 */
package com.ligg.apicommon.controller;

import com.ligg.common.module.entity.EmailEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.EmailService;
import com.ligg.common.utils.Response;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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

    /**
     * 保存邮箱
     */
    @PostMapping
    public Response<String> saveEmail(@NotNull @Email String email) {

        EmailEntity emailEntity = emailService.getEmail( email);
        if (emailEntity != null) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED,"该邮箱已经被登记,请使用未登记的邮箱订阅");
        }
        EmailEntity entity = new EmailEntity();
        entity.setEmail(email);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdateAt(LocalDateTime.now());
        emailService.save(entity);
        return Response.success(BusinessStates.SUCCESS);
    }
}
