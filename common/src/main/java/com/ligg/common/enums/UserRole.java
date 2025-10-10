package com.ligg.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@Getter
@AllArgsConstructor
public enum UserRole {
    USER(1, "普通用户"),
    ADMIN(2, "管理员");

    @EnumValue
    private final Integer code;
    private final String description;

}
