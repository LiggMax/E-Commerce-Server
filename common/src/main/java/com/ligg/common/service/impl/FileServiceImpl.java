/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
package com.ligg.common.service.impl;

import com.ligg.common.config.MinioConfig;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.ImageType;
import com.ligg.common.constants.Constant;
import com.ligg.common.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    // 图片存储根路径
    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    private final MinioConfig minioConfig;

    private final MinioClient minioClient;


    @Override
    public String uploadImage(MultipartFile imageFile, String path) {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("上传文件为空");
        }
        if (imageFile.getSize() > Constant.FILE_SIZE) {
            throw new RuntimeException("上传的文件不能大于2M");
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
            String uniqueFileName = UUID.randomUUID().toString().substring(0, 8) + ".jpg";

            //创建目录
            String datePath = java.time.LocalDate.now().toString();
            String typePath = path == null ? "" : path.contains("/") ? path : '/' + path;
            Path directoryPath = Paths.get(IMAGE_PATH + typePath + '/' + datePath).toAbsolutePath().normalize();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            //构建文件完整路径
            Path filePath = directoryPath.resolve(uniqueFileName);

            // 如果是JPG格式直接保存，否则进行转换
            if (isJpegImage(contentType, originalFilename)) {
                imageFile.transferTo(filePath);
            } else {
                // 转换为JPG格式
                convertToJpeg(imageFile, filePath);
            }

            return Constant.IMAGE_RELATIVE_PATH + typePath + '/' + datePath + '/' + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String minioFileUpload(MultipartFile imageFile, String filePath) {
        String bucketName = minioConfig.getBucketName();
        String datePath = java.time.LocalDate.now().toString();
        String fullFilePath = String.join("/", (filePath != null ? filePath : ""), datePath, UUID.randomUUID() + "_" + imageFile.getOriginalFilename());
        //创建存储桶(如果不存在)
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                //设置访问权限
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(getPublicReadPolicy(bucketName))
                        .build());
            }
        } catch (Exception e) {
            log.error("创建存储桶失败: {}", e.getMessage());
            throw new RuntimeException(BusinessStates.INTERNAL_SERVER_ERROR.getMessage());
        }

        //上传文件
        try (InputStream inputStream = imageFile.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fullFilePath)
                    .stream(inputStream, imageFile.getSize(), -1)
                    .contentType(imageFile.getContentType())
                    .build());
        } catch (Exception e) {
            log.error("上传文件失败: {}", e.getMessage());
        }

        return minioConfig.getEndpoint() + "/" + bucketName + "/" + fullFilePath;
    }

    /**
     * url签名
     */
    private String getUrlSignature(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.GET)
                .build());
    }

    /**
     * 存储桶配置
     * "Principal": "*" 表示允许所有用户访问。
     * "Action": "s3:GetObject" 表示允许下载/读取对象
     * "Action": "s3:PutObject" 表示允许上传/写入对象
     * %s 是格式化占位符，会被传入的 bucketName 替换
     */
    private String getPublicReadPolicy(String bucketName) {
        return """
                {
                  "Version":"2012-10-17",
                  "Statement":[
                    {
                      "Effect":"Allow",
                      "Principal":"*",
                      "Action":["s3:GetObject"],
                      "Resource":["arn:aws:s3:::%s/*"]
                    }
                  ]
                }
                """.formatted( bucketName);
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

        Path imagePath = Paths.get(IMAGE_PATH, path, date, imageName).toAbsolutePath().normalize();
        if (!Files.exists(imagePath)) {
            throw new RuntimeException("图片资源不存在不存在");
        }
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
     * 判断是否为JPEG图片
     */
    private boolean isJpegImage(String contentType, String filename) {
        return "image/jpeg".equals(contentType) ||
                (filename != null && (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")));
    }

    /**
     * 转换图片为JPG格式
     */
    private void convertToJpeg(MultipartFile imageFile, Path targetPath) throws IOException {
        try (InputStream inputStream = imageFile.getInputStream()) {
            Thumbnails.of(inputStream)
                    .scale(1.0) // 保持原尺寸
                    .outputFormat("jpg")
                    .toFile(targetPath.toFile());
        }
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
        Path imagePath = Paths.get(IMAGE_PATH, path, date, imageName).toAbsolutePath().normalize();
        if (Files.exists(imagePath)) {
            throw new RuntimeException("图片资源不存在");
        }
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
        Path imagePath = Paths.get(getBasePath(), filePath).toAbsolutePath().normalize();
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
        if (!StringUtils.hasText(filePath)) {
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

    /**
     * 获取基础路径
     *
     * @return 绝对路径
     */
    private String getBasePath() {
        // 如果IMAGE_PATH已经是绝对路径，则直接返回
        Path basePath = Paths.get(IMAGE_PATH);
        if (basePath.isAbsolute()) {
            return IMAGE_PATH;
        }
        // 如果是相对路径，则基于当前工作目录
        return Paths.get(System.getProperty("user.dir"), IMAGE_PATH).toString();
    }
}
