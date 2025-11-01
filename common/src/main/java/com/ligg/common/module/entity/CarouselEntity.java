package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ligg.common.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/9/23
 * <p>
 * 轮播图实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("carousel")
public class CarouselEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @NotNull
    @Pattern(regexp = "^.{1,20}$", message = "标题长度不能超过20个字符")
    private String title;

    /**
     * 副标题
     */
    @Pattern(regexp = "^.{1,30}$", message = "副标题长度不能超过30个字符")
    private String subtitle;

    /**
     * 描述
     */
    @NotNull
    @Pattern(regexp = "^.{1,200}$", message = "描述长度不能超过200个字符")
    private String description;

    /**
     * 状态
     */
    @NotNull
    private UserStatus status;

    //TODO 类型需要修改成枚举
    /**
     * 打开方式
     */
    @NotNull
    @Pattern(regexp = "^.{1,20}$", message = "打开方式长度不能超过20个字符")
    private String target;

    /**
     * 排序值
     */
    @NotNull
    private Integer sort;


    /**
     * 图片地址
     */
    @NotNull
    @Pattern(regexp = "^.{1,255}$", message = "图片地址长度不能超过255个字符")
    private String imagePath;

    /**
     * 跳转链接
     */
    @NotNull
    @Pattern(regexp = "^.{1,255}$", message = "跳转链接长度不能超过255个字符")
    private String link;

    /**
     * 按钮文字
     */
    @NotNull
    @Pattern(regexp = "^.{1,20}$", message = "按钮文字长度不能超过20个字符")
    private String buttonText;

    /**
     * 创建时间
     */
    @NotNull
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @NotNull
    private LocalDateTime updateAt;
}
