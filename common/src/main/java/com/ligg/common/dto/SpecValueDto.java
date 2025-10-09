package com.ligg.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SpecValueDto {

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
}
