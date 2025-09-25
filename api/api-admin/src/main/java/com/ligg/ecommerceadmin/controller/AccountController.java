package com.ligg.ecommerceadmin.controller;

import com.ligg.ecommerceadmin.service.TokenService;
import com.ligg.ecommerceadmin.service.UserService;
import com.ligg.entity.UserEntity;
import com.ligg.statuEnum.BusinessStates;
import com.ligg.utils.BCryptUtil;
import com.ligg.utils.Response;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
@RestController
@RequestMapping("/api/admin/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 注册账户
     */
    @PostMapping("/register")
    public Response<String> register(@NotNull String account, @NotNull String password) {
        if (account.length() < 6 || account.length() > 30 || password.length() < 6 || password.length() > 30) {
            return Response.error(BusinessStates.VALIDATION_FAILED);
        }

        if (userService.getUserInfoByAccount(account) != null) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "该账号已被注册");
        }
        userService.register(account, password);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Response<String> login(@RequestParam @NotNull String account, @RequestParam @NotNull String password) {
        UserEntity userInfo = userService.getUserInfoByAccount(account);
        if (userInfo == null) {
            return Response.error(BusinessStates.FORBIDDEN, "账号或密码错误");
        }
        if (!BCryptUtil.verify(password, userInfo.getPassword())) {
            return Response.error(BusinessStates.FORBIDDEN, "账号或密码错误");
        }
        String token = tokenService.generateToken(userInfo);
        tokenService.saveToken(token, userInfo.getUserId());
        return Response.success(BusinessStates.SUCCESS, token);
    }
}
