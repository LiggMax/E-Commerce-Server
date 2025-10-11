package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
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
@TableName("spec")
public class SpecEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    @NotNull
    private String productId;

    /**
     * 规格名称
     */
    @NotNull
    private String name;

    /**
     * 排序
     */
    @NotNull
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
