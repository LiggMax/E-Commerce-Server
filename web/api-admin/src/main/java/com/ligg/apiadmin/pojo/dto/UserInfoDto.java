package com.ligg.apiadmin.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ligg.common.enums.StatusEnum;
import com.ligg.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private String userId;

    /**
     * 昵称
     */
    private String nickName;


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
    private String email;
}
