package com.ligg.common.module.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


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
    @Schema(description = "标题")
    private String title;

    /**
     * 副标题
     */
    @Schema(description = "副标题")
    private String subtitle;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 状态
     */
    @Schema(description = "状态")
    @NotNull
    private Boolean status;

    /**
     * 打开方式
     */
    @Schema(description = "打开方式")
    private String target;

    /**
     * 排序值
     */
    @Schema(description = "排序值")
    private Integer sort;


    /**
     * 图片地址
     */
    @Schema(description = "图片地址s")
    private ImagesVo images;

    /**
     * 跳转链接
     */
    @Schema(description = "跳转链接")
    private String link;

    /**
     * 按钮文字
     */
    @Schema(description = "按钮文字")
    private String buttonText;
}

