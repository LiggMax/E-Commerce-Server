package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ligg.common.enums.AuditStatu;
import com.ligg.common.enums.Whether;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product_comment")
public class ProductCommentEntity {

    @TableId(type = IdType.AUTO)
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
    private Map<String,Object> images;

    /**
     * 评论类型
     */
    private Integer type;

    /**
     * 评论状态
     */
    private AuditStatu status;

    /**
     * 是匿名评论
     */
    private Whether isAnonymous;

    /**
     * 评论时的ip
     */
    private String ipAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
