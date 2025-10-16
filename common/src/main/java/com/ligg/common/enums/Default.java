package com.ligg.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
@Getter
@AllArgsConstructor
public enum Default {
    YES(1, true),
    NO(0, false);

    @EnumValue
    private final Integer code;
    private final boolean isDefault;
}
