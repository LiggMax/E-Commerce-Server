package com.ligg.common.module.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/9/30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageVo {
    private Integer id;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 图片路径
     */
    private String url;
}
