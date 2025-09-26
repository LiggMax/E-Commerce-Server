package com.ligg.common.service.impl;

import com.ligg.common.Imagenum.ImageType;
import com.ligg.common.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    /**
     * 获取多质量图片输入流
     *
     * @param type      图片类型
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    @Override
    public StreamingResponseBody getImageInputStream(String type, String date, String imageName) {

        Path imagePath = Paths.get(IMAGE_PATH, date, imageName);
        Files.exists(imagePath);
        ImageType imageType = ImageType.valueOf(type.toLowerCase());

        return outputStream -> {
            try (InputStream inputStream = Files.newInputStream(imagePath)) {
                Thumbnails.of(inputStream)
                        .scale(imageType.getScale())
                        .outputQuality(imageType.getQuality())
                        .outputFormat("jpg")
                        .toOutputStream(outputStream);
            }
        };
    }

    /**
     * 获取图片输入流
     *
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    @Override
    public StreamingResponseBody getImageInputStream(String date, String imageName) {
        Path imagePath = Paths.get(IMAGE_PATH, date, imageName);
        Files.exists(imagePath);
        return outputStream -> {
            try (InputStream inputStream = Files.newInputStream(imagePath)) {
                inputStream.transferTo(outputStream);
            }
        };
    }
}
