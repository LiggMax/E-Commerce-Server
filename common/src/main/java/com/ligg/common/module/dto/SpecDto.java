/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
package com.ligg.common.module.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecDto {

    /**
     * 商品id
     */
    @NotNull
    private String productId;

    /**
     * 规格
     */
    private List<Specs> specs;

    @Getter
    @Setter
    public static class Specs {
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
         * 规格内容
         */
        private List<SpecValue> specValues;


    }

    @Setter
    @Getter
    public static class SpecValue {


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
        private Double price;
    }
}
