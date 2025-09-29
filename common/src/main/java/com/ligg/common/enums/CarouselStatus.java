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
public enum CarouselStatus {
    ENABLED(1, "启用",true),
    DISABLED(0, "禁用", false);

    @EnumValue
    private final Integer code;
    private final String description;
    private final boolean isEnabled;
}
