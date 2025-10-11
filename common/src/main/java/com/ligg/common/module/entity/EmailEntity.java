package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author LiGG
 * @Time 2025/10/8
 */

@Data
@TableName("email")
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;
}
