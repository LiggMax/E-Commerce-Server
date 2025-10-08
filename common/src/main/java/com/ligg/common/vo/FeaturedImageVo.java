package com.ligg.common.vo;

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
public class FeaturedImageVo {
    private Integer id;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 图片路径
     */
    private String imagePath;
}
