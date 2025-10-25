package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Ligg
 * @Time 2025/10/25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product_favorite")
public class ProductFavoriteEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
