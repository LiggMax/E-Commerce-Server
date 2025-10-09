package com.ligg.common.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecVo {
    private Integer id;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 规格值
     */
    private List<SpecValueVo> specValues;

    @Getter
    @Setter
    public static class SpecValueVo {
        private Integer id;

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
}
