/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.entity.UserEntity;
import com.ligg.common.service.TokenService;
import com.ligg.common.service.UserService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户接口
 */
@Tag(name = "账户接口")
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
    //暂时不提供该接口
    //@PostMapping("/register")
    public Response<String> register(@Schema(description = "账号") @NotNull String account,
                                     @Schema(description = "密码") @NotNull String password) {
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
    public Response<String> login(@Schema(description = "账号") @RequestParam @NotNull String account,
                                  @Schema(description = "密码") @RequestParam @NotNull String password) {
        UserEntity userInfo = userService.getUserInfoByAccount(account);
        if (userInfo == null || !BCryptUtil.verify(password, userInfo.getPassword())) {
            return Response.error(BusinessStates.FORBIDDEN, "账号或密码错误");
        }
        String token = tokenService.generateToken(userInfo);
        tokenService.saveToken(token, userInfo.getUserId());
        return Response.success(BusinessStates.SUCCESS, token);
    }
}
