/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
package com.ligg.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 图片s
 */
@Data
public class ImagesVo {

    /**
     * 大图片
     */
    @Schema(description = "大图片")
    private String largeImage;

    /**
     * 小图片
     */
    @Schema(description = "小图片")
    private String smallImage;
}
