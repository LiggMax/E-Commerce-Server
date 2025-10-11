package com.ligg.common.module.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "轮播图数据")
public class CarouselDto {

    /**
     * id
     */
    @Schema(description = "id")
    private Integer id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @NotNull
    @Pattern(regexp = "^.{1,20}$", message = "标题长度不能超过20个字符")
    private String title;

    /**
     * 副标题
     */
    @Schema(description = "副标题")
    @Pattern(regexp = "^.{1,30}$", message = "副标题长度不能超过30个字符")
    private String subtitle;

    /**
     * 描述
     */
    @NotNull
    @Schema(description = "描述")
    @Pattern(regexp = "^.{1,200}$", message = "描述长度不能超过200个字符")
    private String description;

    /**
     * 状态
     */
    @NotNull
    @Schema(description = "状态")
    private Boolean status;

    /**
     * 打开方式
     */
    @NotNull
    @Schema(description = "打开方式")
    @Pattern(regexp = "^.{1,20}$", message = "打开方式长度不能超过20个字符")
    private String target;

    /**
     * 排序值
     */
    @NotNull
    @Schema(description = "排序值")
    private Integer sort;


    /**
     * 跳转链接
     */
    @Schema(description = "跳转链接")
    private String link;

    /**
     * 按钮文字
     */
    @NotNull
    @Schema(description = "按钮文字")
    @Pattern(regexp = "^.{1,20}$", message = "按钮文字长度不能超过20个字符")
    private String buttonText;
}
