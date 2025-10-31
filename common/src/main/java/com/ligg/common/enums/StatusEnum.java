package com.ligg.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Ligg
 * @Time 2025/9/29
 **/
@Getter
@AllArgsConstructor
public enum StatusEnum {
    ENABLED(1 ,true),
    DISABLED(0, false);

    @EnumValue
    private final Integer code;
    private final boolean isEnabled;

    public static StatusEnum fromBoolean(boolean enabled) {
        return enabled ? ENABLED : DISABLED;
    }

    public boolean getStatus() {
        return this.isEnabled;
    }
}
