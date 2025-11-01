package com.ligg.apiadmin.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
@Data
public class SystemInfoVo {

    /**
     * 用户总数
     */
    private long userCount;

    /**
     * 今日订单数
     */
    private long todayOrderCount;

    /**
     * 订单总金额
     */
    private BigDecimal orderTotalAmount;
}
