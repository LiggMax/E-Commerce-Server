/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户地址
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressEntity {
    private Long id;

    /**
     * 收货人名称
     */
    private String receiverName;

    /**
     * 收货人手机号码
     */
    private String phone;

    /**
     * 是否默认地址
     */
    private Boolean isDefault;

    /**
     * 收货地址
     */
    private String detail;

    /**
     * 创建时间
     */
    private Long createTime;
}
