package com.ligg.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/9/30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("featured_image")
public class FeaturedImageEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    private String featuredId;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 图片路径
     */
    private String imagePath;
}
