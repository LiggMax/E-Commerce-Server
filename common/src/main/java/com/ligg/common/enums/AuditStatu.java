package com.ligg.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@AllArgsConstructor
@Getter
public enum AuditStatu {
    PENDING_REVIEW(0, "禁用"),
    APPROVED(1, "通过"),
    REJECTED(2, "拒绝");

    @EnumValue
    private final int code;
    private final String message;
}
