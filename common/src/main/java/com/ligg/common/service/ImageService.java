package com.ligg.common.service;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
public interface ImageService {

    /**
     * 获取多种图片输入流
     *
     * @param type      图片类型
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    StreamingResponseBody getImageInputStream(String type, String date, String imageName);

    /**
     * 获取图片输入流
     *
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    StreamingResponseBody getImageInputStream(String date, String imageName);
}
