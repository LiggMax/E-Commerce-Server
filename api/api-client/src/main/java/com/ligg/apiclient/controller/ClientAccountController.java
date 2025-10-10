package com.ligg.apiclient.controller;

import com.ligg.apiclient.service.ClientAccountService;
import com.ligg.common.dto.AccountDto;
import com.ligg.common.entity.UserEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.CaptchaService;
import com.ligg.common.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/account")
public class ClientAccountController {

    final ClientAccountService clientAccountService;
    final CaptchaService captchaService;

    /**
     * 注册
     */
    @RequestMapping("/register")
    public Response<String> register(@Validated @RequestBody AccountDto account) {
        if (!StringUtils.hasText(account.getCode()) || account.getCode().length() < 6) {
            return Response.error(BusinessStates.BAD_REQUEST);
        }
        boolean isTrue = captchaService.verifyCaptcha(account.getCode(), account.getUuid());
        if (!isTrue) {
            return Response.error(BusinessStates.VALIDATION_FAILED, "验证码错误");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(account, userEntity);
        clientAccountService.register(userEntity);
        return Response.success(BusinessStates.SUCCESS);
    }
}
