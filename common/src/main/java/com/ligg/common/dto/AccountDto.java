package com.ligg.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Data
public class AccountDto {
    private String nickName;

    /**
     * 账号
     * 必须大于六位，不能是123456
     */
    @NotNull
    private String account;

    /**
     * 密码必须大于6位等于6，不能是123456
     */
    @NotNull
    private String password;

    private String avatar;

    /**
     * 验证码
     */
    private String code;

    /**
     * uuid
     */
    private String uuid;

    String email;
    private Integer status;

    private LocalDateTime createTime;
}
