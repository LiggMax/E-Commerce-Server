package com.ligg.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/9/23
 *
 * 轮播图实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("carousel")
public class CarouselEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private Integer status;

    /**
     * 跳转目标
     */
    private String target;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 跳转链接
     */
    private String link;

    /**
     * 按钮文字
     */
    private String buttonText;


}
