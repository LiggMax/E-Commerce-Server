package com.ligg.common.module.vo;

import com.ligg.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号
     */
    private String account;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色 1-普通用户 2-管理员
     */
    private UserRole role;

    /**
     * 状态 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账户余额
     */
    private BigDecimal accountBalance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

}
