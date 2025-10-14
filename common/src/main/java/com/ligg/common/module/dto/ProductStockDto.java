/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
package com.ligg.common.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品库存
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDto {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品库存
     */
    private Integer stock;
}
