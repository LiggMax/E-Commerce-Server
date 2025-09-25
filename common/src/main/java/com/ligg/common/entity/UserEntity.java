package com.ligg.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
@Data
@TableName("user")
public class UserEntity {
    @TableId
    private String userId;
    private String nickName;
    private String password;
    private String account;
    private String avatar;
    private Integer role;
    private Integer status;
    private LocalDateTime createTime;
}
