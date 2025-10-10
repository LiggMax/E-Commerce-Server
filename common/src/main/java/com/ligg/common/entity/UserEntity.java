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
    private String userId;
    private String nickName;
    private String password;
    private String account;
    private String avatar;
    private UserRole role;
    private Integer status;
    private LocalDateTime createTime;
}
