package com.ligg.common.utils;

import com.ligg.common.Imagenum.ImageType;
import com.ligg.common.vo.CarouselVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
@Component
public class ImageUtil {

    @Value("${api.base-url}")
    private String BASEURL;

    public CarouselVo.Images getImagePath(String imagePath) {
        CarouselVo.Images images = new CarouselVo.Images();

        String smallImagePath = imagePath.replace("/image/", "/image/" + ImageType.small.name() + '/');
        String largeImagePath = imagePath.replace("/image/", "/image/" + ImageType.large.name() + '/');
        images.setSmallImage(BASEURL + smallImagePath);
        images.setLargeImage(BASEURL + largeImagePath);
        return images;
    }
}
