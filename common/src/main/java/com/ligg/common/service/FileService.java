package com.ligg.common.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface FileService {


    /**
     * 上传图片文件到指定路径
     * @param imageFile 图片文件
     * @param path 指定的路径（可选）
     * @return 图片访问路径
     */
     String uploadImage(MultipartFile imageFile, String path);

    /**
     * 获取多种图片输入流
     *
     * @param type      图片类型
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    StreamingResponseBody getImageInputStream(String path,String type, String date, String imageName);

    /**
     * 获取图片输入流
     *
     * @param path
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    StreamingResponseBody getImageInputStream(String path,String date, String imageName);

    /**
     * @param filePath
     * 删除文件
     */
    void deleteFile(String filePath);
}
