/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.apiclient.controller;

import com.ligg.apiclient.service.ClientAccountService;
import com.ligg.common.enums.UserRole;
import com.ligg.common.module.dto.AccountDto;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.CaptchaService;
import com.ligg.common.service.TokenService;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 账户接口
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/account")
public class ClientAccountController {

    private final UserService userService;
    private final TokenService tokenService;
    private final CaptchaService captchaService;
    private final ClientAccountService clientAccountService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public Response<String> register(@Validated @RequestBody AccountDto account) {
        if (account.getAccount().length() < 6 || account.getAccount().length() > 20 ||
                account.getPassword().length() < 6 || account.getPassword().length() > 20) {
            return Response.error(BusinessStates.VALIDATION_FAILED);
        }
        if (!StringUtils.hasText(account.getCode()) || account.getCode().length() != 6) {
            return Response.error(BusinessStates.VALIDATION_FAILED);
        }
        boolean isTrue = captchaService.verifyCaptcha(account.getCode(), account.getUuid());
        if (!isTrue) {
            return Response.error(BusinessStates.VALIDATION_FAILED, "验证码错误");
        }

        if (userService.getUserInfoByAccount(account.getAccount()) != null) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "该账号已被注册");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(account.getAccount());
        userEntity.setPassword(BCryptUtil.encrypt(account.getPassword()));
        userEntity.setEmail(account.getEmail());
        userEntity.setRole(UserRole.USER);
        userEntity.setNickName("user_" + UUID.randomUUID().toString().substring(0, 6));
        userEntity.setCreateTime(LocalDateTime.now());
        return clientAccountService.register(userEntity) < 1
                ? Response.error(BusinessStates.INTERNAL_SERVER_ERROR)
                : Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public Response<String> login(@Validated @RequestBody AccountDto account) {
        UserEntity userInfo = userService.getUserInfoByAccount(account.getAccount());
        if (userInfo == null || !BCryptUtil.verify(account.getPassword(), userInfo.getPassword())) {
            return Response.error(BusinessStates.FORBIDDEN, "账号或密码错误");
        }
        String token = tokenService.generateToken(userInfo);
        if (token == null) {
            return Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
        }
        tokenService.saveToken(token, userInfo.getUserId());
        return Response.success(BusinessStates.SUCCESS,token);
    }
}
