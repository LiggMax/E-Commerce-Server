package com.ligg.common.Imagenum;

import lombok.Getter;

/**
 * @Author Administrator
 * @Time 2025/9/17
 **/

@Getter
public enum ImageType {
    large(1, 1),
    common(0.8, 0.8F),
    medium(0.6, 0.6F),
    small(0.5, 0.5F),
    grid(0.4, 0.4F);
    private final float quality;
    private final double scale;

    ImageType(double scale, float quality) {
        this.scale = scale;
        this.quality = quality;
    }
}
