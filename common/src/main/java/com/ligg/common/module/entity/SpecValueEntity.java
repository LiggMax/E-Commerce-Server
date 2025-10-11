package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("spec_value")
public class SpecValueEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 规格id
     */
    private Integer specId;

    /**
     * 值(规则内容)
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 规格价格
     */
    private Integer price;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
