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
public enum Whether {
    YES(1, "是"),
    NO(0, "否");

    @EnumValue
    private final Integer code;
    private final String message;
}
