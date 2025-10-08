package com.ligg.common.vo.search;

import com.ligg.common.vo.ImagesVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
}
