package com.ligg.common.module.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author Ligg
 * @Time 2025/10/22
 **/
@Data
public class UserDto {
    /**
     * 昵称
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_-]{2,16}$", message = "昵称格式错误")
    private String nickName;

    /**
     * 邮箱
     */
    @NotNull
    @Email
    private String email;
}
