package com.ligg.common.module.vo;

import com.ligg.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {

    private String nickName;
    private String account;
    private String avatar;
    private UserRole role;
    private Integer status;
    private String email;
    private LocalDateTime createTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

}
