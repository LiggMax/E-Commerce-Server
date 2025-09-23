package com.ligg.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传图片文件
     * @param imageFile
     * @return
     */
    String uploadImage(MultipartFile imageFile);
}
