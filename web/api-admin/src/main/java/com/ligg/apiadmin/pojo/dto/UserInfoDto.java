package com.ligg.apiadmin.pojo.dto;

import com.ligg.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/10/31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    /**
     * 用户id
     */
    @NotNull
    private String userId;

    /**
     * 昵称
     */
    @NotNull
    private String nickName;

    /**
     * 状态
     */
    private Boolean isStatus;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色 1-普通用户 2-管理员
     */
    private UserRole role;

    /**
     * 邮箱
     */
    @Email
    private String email;
}
