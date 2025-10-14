/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单明细规格
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_item_spec")
public class OrderItemSpecEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单明细id
     */
    private Long orderItemId;

    /**
     * 规格值id
     */
    private Integer specValueId;
}
