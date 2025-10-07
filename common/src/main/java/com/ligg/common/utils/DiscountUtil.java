/**
 * @Author LiGG
 * @Time 2025/10/6
 */
package com.ligg.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountUtil {

    /**
     * 计算折扣百分比
     * 公式为：折扣率 = (原价-现价)/原价 × 100%
     * @param originalPrice 原价
     * @param currentPrice 现价
     */
    public static BigDecimal calculateDiscountPercentage(Double originalPrice, Double currentPrice) {
        BigDecimal original = BigDecimal.valueOf(originalPrice);
        BigDecimal current = BigDecimal.valueOf(currentPrice);
        return original.subtract(current)
                .divide(original, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
