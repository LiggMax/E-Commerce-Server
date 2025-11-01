package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ligg.common.enums.UserStatus;
import com.ligg.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserEntity {

    /**
     * 用户id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

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
    private UserStatus status;

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
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
}
