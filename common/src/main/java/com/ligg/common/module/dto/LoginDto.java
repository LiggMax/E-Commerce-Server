package com.ligg.common.module.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ligg
 * @Time 2025/10/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    /**
     * 账号
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "参数不合法")
    private String account;

    /**
     * 密码
     */
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "参数不合法")
    private String password;
}
