package com.ligg.common.module.vo.search;

import com.ligg.common.module.vo.ImagesVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Ligg
 * @Time 2025/10/8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVo {

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 封面s
     */
    private ImagesVo url;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 现价
     */
    private Double currentPrice;

    /**
     * 评价
     */
    private Integer reviews;

    /**
     * 折扣
     */
    @Schema(description = "折扣")
    private Double discount;
}
