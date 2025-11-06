/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ligg.common.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("product")
public class ProductEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 名称
     */
    private String title;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 现价
     */
    private Double currentPrice;

    /**
     * 评价数
     */
    private Integer reviews;

    /**
     * 评分
     */
    private int rating;

    /**
     * 状态
     */
    private UserStatus status;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 阅览量
     */
    private Integer views;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;
}
