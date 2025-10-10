package com.ligg.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ligg.common.enums.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
@Data
@TableName("user")
public class UserEntity {

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    String userId;
    String nickName;
    String password;
    String account;
    String avatar;
    UserRole role;
    Integer status;
    LocalDateTime createTime;
    String email;

    /**
     * 最后登录时间
     */
    LocalDateTime lastLoginTime;
}
