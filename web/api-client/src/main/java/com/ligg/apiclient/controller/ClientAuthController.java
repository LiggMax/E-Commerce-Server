package com.ligg.apiclient.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ligg.apiclient.service.ClientAccountService;
import com.ligg.common.enums.UserRole;
import com.ligg.common.enums.UserStatus;
import com.ligg.apiclient.pojo.dto.AccountDto;
import com.ligg.apiclient.pojo.dto.LoginDto;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.CaptchaService;
import com.ligg.common.service.EmailService;
import com.ligg.common.service.TokenService;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Tag(name = "账户接口")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/auth")
public class ClientAuthController {

    private final UserService userService;

    private final TokenService tokenService;

    private final CaptchaService captchaService;

    private final EmailService emailService;

    private final ClientAccountService clientAccountService;

    @PostMapping("/register")
    @Operation(summary = "注册", description = "提交注册信息获取邮件验证码")
    public Response<String> register(@Validated @RequestBody AccountDto account) {
        boolean isTrue = captchaService.verifyCaptcha(account.getCode(), account.getUuid());
        if (!isTrue) {
            return Response.error(BusinessStates.VALIDATION_FAILED, "验证码错误");
        }

        if (emailService.getEmail(account.getEmail()) != null) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "该邮箱已被注册,请更换邮箱.");
        }

        if (emailService.canSendVerificationCode(account.getEmail())) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "请勿重复发送验证码");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(BCryptUtil.encrypt(account.getPassword()));
        userEntity.setEmail(account.getEmail());
        userEntity.setRole(UserRole.USER);
        if (account.getNickName() == null) {
            userEntity.setNickName("user_" + UUID.randomUUID().toString().substring(0, 6));
        } else {
            userEntity.setNickName(account.getNickName());
        }
        userEntity.setCreateTime(LocalDateTime.now());
        emailService.sendVerificationCode(account.getEmail());
        clientAccountService.addRegisterInfoToCache(userEntity);
        return Response.success(BusinessStates.SUCCESS);
    }

    @PostMapping("/verify")
    @Operation(summary = "注册校验", description = "提交提交获取的邮件完成注册")
    public Response<String> verify(@Email String email,
                                   @NotNull int emailCode) {
        UserEntity redisUserInfo = clientAccountService.getRegisterInfo(email);
        if (redisUserInfo == null) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "验证码已过期");
        }
        if (!emailService.verifyEmailCode(email, emailCode)) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "验证码错误或已过期");
        }
        return clientAccountService.register(redisUserInfo) > 0
                ? Response.success(BusinessStates.SUCCESS)
                : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/login")
    @Operation(summary = "登录", description = "提交登录信息获取token")
    public Response<String> login(@Validated @RequestBody LoginDto account) {
        UserEntity userInfo = userService.getUserInfoByAccount(account.getAccount());
        if (userInfo == null || !BCryptUtil.verify(account.getPassword(), userInfo.getPassword())) {
            return Response.error(BusinessStates.FORBIDDEN, "账号或密码错误");
        }
        if (userInfo.getStatus().equals(UserStatus.DISABLED)) {
            return Response.error(BusinessStates.FORBIDDEN, "账号已被禁用");
        }
        String token = tokenService.generateToken(userInfo);
        if (token == null) {
            return Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
        }
        tokenService.saveToken(token, userInfo.getUserId());
        return Response.success(BusinessStates.SUCCESS, token);
    }

    /**
     * 找回密码
     */
    @Operation(summary = "找回密码")
    @PutMapping("/forget")
    public Response<String> forget(@Pattern(regexp = "^[a-zA-Z0-9_-]{6,20}$", message = "参数不合法") String account,
                                   @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "参数不合法") String password,
                                   @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "参数不合法") String code,
                                   String uuid) {
        if (!captchaService.verifyCaptcha(code, uuid)) {
            Response.error(BusinessStates.VALIDATION_FAILED, "验证码错误");
        }
        UserEntity userInfo = userService.getUserInfoByAccount(account);

        if (userInfo == null) {
            return Response.error(BusinessStates.NOT_FOUND, "查找的账户不存在");
        }

        userService.update(new LambdaUpdateWrapper<UserEntity>()
                .eq(UserEntity::getAccount, account)
                .set(UserEntity::getPassword, BCryptUtil.encrypt(password))
                .set(UserEntity::getUpdateTime, LocalDateTime.now()));
        return Response.success(BusinessStates.SUCCESS);
    }
}
