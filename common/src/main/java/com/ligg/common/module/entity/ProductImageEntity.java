/**
 * @Author Ligg
 * @Time 2025/9/30
 **/
package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product_image")
public class ProductImageEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 图片路径
     */
    private String imagePath;
}
