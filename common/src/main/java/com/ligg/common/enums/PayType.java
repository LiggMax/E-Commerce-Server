/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付类型
 */
@Getter
@AllArgsConstructor
public enum PayType {
    ALI_PAY(1, "支付宝"),
    WX_PAY(2, "微信支付");

    @EnumValue
    private final Integer code;
    private final String description;

}
