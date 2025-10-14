package com.ligg.common.constants;

/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
public class OrderConstant {
    //十分钟
    public static final long ORDER_TIMEOUT = 10 * 60 * 1000;

    /**
     * 订单锁前缀
     */
    public static final String ORDER_LOCK_KEY = "order:create:";
}
