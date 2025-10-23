package com.ligg.common.module.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommentVo {

    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评分
     */
    private Integer rating;

    /**
     * 图片(json数组)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> images;

    /**
     * 评论类型
     */
    private Integer type;

    /**
     * 评论时的ip
     */
    private String ipAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
