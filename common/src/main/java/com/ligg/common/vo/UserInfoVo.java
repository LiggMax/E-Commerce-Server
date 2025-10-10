package com.ligg.common.vo;

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

    String nickName;
    String password;
    String account;
    String avatar;
    UserRole role;
    Integer status;
    String email;

    LocalDateTime createTime;
    /**
     * 最后登录时间
     */
    LocalDateTime lastLoginTime;

}
