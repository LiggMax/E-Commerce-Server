package com.ligg.common.utils;

import com.ligg.common.enums.ImageType;
import com.ligg.common.module.vo.ImagesVo;

/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
public class ImageUtil {

    //TODO 需要修改不使用String直接拼接字符串，因为该方法可能会被循环遍历拼接字符串
    public static ImagesVo getImagePath(String imagePath) {
        ImagesVo images = new ImagesVo();

        String smallImagePath = imagePath.replace("/image", "/image/" + ImageType.small.name());
        String largeImagePath = imagePath.replace("/image", "/image/" + ImageType.large.name());
        images.setSmallImage(smallImagePath);
        images.setLargeImage(largeImagePath);
        return images;
    }
}
