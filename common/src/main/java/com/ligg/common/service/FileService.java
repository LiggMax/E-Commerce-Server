package com.ligg.common.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface FileService {


    /**
     * 上传图片文件到指定路径
     *
     * @param imageFile 图片文件
     * @param FilePath  指定文件保存的路径
     * @return 图片访问路径
     */
    String uploadImage(MultipartFile imageFile, String FilePath);

    /**
     * 获取多种图片输入流
     *
     * @param type      图片类型
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    StreamingResponseBody getImageInputStream(String path, String type, String date, String imageName);

    /**
     * 获取图片输入流
     *
     * @param path      图片路径
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    StreamingResponseBody getImageInputStream(String path, String date, String imageName);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @deprecated 请使用 {@link #deleteFileAsync(String)} 替代
     */
    @Deprecated
    void deleteFile(String filePath);

    /**
     * 异步删除文件
     *
     * @param filePath 文件路径
     */
    void deleteFileAsync(String filePath);
}
