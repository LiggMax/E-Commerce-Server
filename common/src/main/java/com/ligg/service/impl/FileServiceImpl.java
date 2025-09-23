package com.ligg.service.impl;

import com.ligg.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    // 图片存储根路径
    @Value("${file.image.base-path}")
    private String IMAGE_PATH;
    // 图片访问的相对路径前缀
    @Value("${file.image.relative-path}")
    private String IMAGE_RELATIVE_PATH;


    @Override
    public String uploadImage(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("上传文件为空");
        }

        //验证文件类型
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("上传的文件不是有效的图片类型");
        }

        try {
            //获取文件原始名称
            String originalFilename = imageFile.getOriginalFilename();

            // 生成唯一文件名
            String uniqueFileName = UUID.randomUUID().toString().substring(0, 8) + '-' + originalFilename;

            //创建目录
            String datePath = java.time.LocalDate.now().toString();
            Path directoryPath = Paths.get(IMAGE_PATH + '/' + datePath);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            //构建文件完整路径
            Path filePath = directoryPath.resolve(uniqueFileName);

            // 保存文件
            imageFile.transferTo(filePath);

            return "api/" +IMAGE_RELATIVE_PATH + '/' + datePath + '/' + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
