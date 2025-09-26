package com.ligg.common.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;


/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselVo {
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    @NotNull
    private Integer status;

    /**
     * 打开方式
     */
    private String target;

    /**
     * 排序值
     */
    private Integer sort;


    /**
     * 图片地址
     */
    private Images images;

    /**
     * 跳转链接
     */
    private String link;

    /**
     * 按钮文字
     */
    private String buttonText;

    /**
     * 图片s
     */
    @Setter
    @Getter
    public static class Images {
        /**
         * 大图片
         */
        private String largeImage;

        /**
         * 小图片
         */
        private String smallImage;
    }
}

