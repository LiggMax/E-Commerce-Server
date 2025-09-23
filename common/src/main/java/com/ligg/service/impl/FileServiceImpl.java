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

    @Value("${file.imagePath}")
    private String IMAGE_PATH;

    @Override
    public String uploadImage(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new RuntimeException("上传文件为空");
        }
        try {
            //获取文件原始名称
            String originalFilename = imageFile.getOriginalFilename();

            // 生成唯一文件名
            String uniqueFileName = UUID.randomUUID().toString() + '-' + originalFilename;

            //创建目录
            String date = java.time.LocalDate.now().toString();
            Path directoryPath = Paths.get(IMAGE_PATH + '/' + date);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            //构建文件完整路径
            Path filePath = directoryPath.resolve(uniqueFileName);

            // 保存文件
            imageFile.transferTo(filePath);

            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
