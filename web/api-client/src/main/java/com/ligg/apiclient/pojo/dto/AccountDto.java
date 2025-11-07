package com.ligg.apiclient.pojo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Data
public class AccountDto {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码必须大于6位等于6，不能是123456
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "参数不合法")
    private String password;

    /**
     * 验证码
     * 必须等于六位
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "参数不合法")
    private String code;

    /**
     * uuid
     */
    @NotNull
    private String uuid;

    @Email
    @NotNull
    private String email;
}
