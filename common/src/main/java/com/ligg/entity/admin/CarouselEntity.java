package com.ligg.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Pattern;
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

    /**
     * 标题
     */
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
    @Pattern(regexp = "^.{1,200}$", message = "描述长度不能超过200个字符")
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 打开方式
     */
    @Pattern(regexp = "^.{1,20}$", message = "打开方式长度不能超过20个字符")
    private String target;

    /**
     * 排序值
     */
    private Integer sort;



    /**
     * 图片地址
     */
    @Pattern(regexp = "^.{1,255}$", message = "图片地址长度不能超过255个字符")
    private String imageUrl;

    /**
     * 跳转链接
     */
    @Pattern(regexp = "^.{1,255}$", message = "跳转链接长度不能超过255个字符")
    private String link;

    /**
     * 按钮文字
     */
    @Pattern(regexp = "^.{1,20}$", message = "按钮文字长度不能超过20个字符")
    private String buttonText;


}
