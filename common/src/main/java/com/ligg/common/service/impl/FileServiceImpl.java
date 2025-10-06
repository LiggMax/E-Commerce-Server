package com.ligg.common.service.impl;

import com.ligg.common.Imagenum.ImageType;
import com.ligg.common.service.FileService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Slf4j
@Service
public class FileServiceImpl implements FileService {

    // 图片存储根路径
    @Value("${file.image.base-path}")
    private String IMAGE_PATH;
    // 图片访问的相对路径前缀
    @Value("${file.image.relative-path}")
    private String IMAGE_RELATIVE_PATH;


    @Override
    public String uploadImage(MultipartFile imageFile, String path) {
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
            String typePath = path == null ? "" : path.contains("/") ? path : '/' + path;
            Path directoryPath = Paths.get(IMAGE_PATH + typePath + '/' + datePath);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            //构建文件完整路径
            Path filePath = directoryPath.resolve(uniqueFileName);

            // 保存文件
            imageFile.transferTo(filePath);

            return IMAGE_RELATIVE_PATH + typePath + '/' + datePath + '/' + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取多质量图片输入流
     *
     * @param type      图片类型
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    @Override
    public StreamingResponseBody getImageInputStream(String path, String type, String date, String imageName) {

        Path imagePath = Paths.get(IMAGE_PATH, path, date, imageName);
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
     * @param path      图片路径
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片输入流
     */
    @Override
    public StreamingResponseBody getImageInputStream(String path, String date, String imageName) {
        Path imagePath = Paths.get(IMAGE_PATH, path, date, imageName);
        Files.exists(imagePath);
        return outputStream -> {
            try (InputStream inputStream = Files.newInputStream(imagePath)) {
                inputStream.transferTo(outputStream);
            }
        };
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    @Override
    @Deprecated
    public void deleteFile(String filePath) {
        Path imagePath = Paths.get(IMAGE_PATH, filePath);
        //检查文件是否存在
        if (Files.exists(imagePath)) {
            try {
                Files.delete(imagePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 异步删除文件
     *
     * @param filePath 文件路径
     */
    @Override
    @Async("fileTaskExecutor")
    public void deleteFileAsync(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            log.warn("文件路径为空");
            return;
        }
        try {
            Path imagePath = Paths.get(filePath);
            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
                log.info("异步删除文件成功:{}", filePath);
            }
        } catch (IOException e) {
            log.error("文件删除失败: {}, 错误信息: {}", filePath, e.getMessage());
        }
    }
}
