package com.ligg.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselVo {
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    @NotNull
    private Integer status;

    /**
     * 打开方式
     */
    private String target;

    /**
     * 排序值
     */
    private Integer sort;


    /**
     * 图片地址
     */
    private String imagePath;

    /**
     * 跳转链接
     */
    private String link;

    /**
     * 按钮文字
     */
    private String buttonText;
}
