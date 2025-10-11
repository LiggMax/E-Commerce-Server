/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态枚举
 */
@Getter
@AllArgsConstructor
public enum PayStatus {
    UNPAID(1,"未支付"),
    PAID(2,"支付成功");

    private final int  code;
    private final String description;
}
