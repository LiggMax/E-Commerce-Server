package com.ligg.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {


    /**
     * 上传图片文件到指定路径
     * @param imageFile 图片文件
     * @param path 指定的路径（可选）
     * @return 图片访问路径
     */
     String uploadImage(MultipartFile imageFile, String path);
}
