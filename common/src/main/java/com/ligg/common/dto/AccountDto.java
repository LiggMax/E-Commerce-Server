package com.ligg.common.dto;

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
    private String nickName;

    /**
     * 账号
     * 必须大于六位，不能是123456
     */
    @NotNull
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$", message = "账号必须大于六位，不能是123456")
    private String account;

    /**
     * 密码必须大于6位，不能是123456
     */
    @NotNull
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$", message = "密码必须大于6位，不能是123456")
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

    private Integer status;
    private LocalDateTime createTime;
}
