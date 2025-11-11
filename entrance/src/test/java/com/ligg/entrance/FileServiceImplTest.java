//package com.ligg.entrance;
//
//import com.ligg.common.config.MinioConfig;
//import com.ligg.common.enums.BusinessStates;
//import com.ligg.common.service.impl.FileServiceImpl;
//import io.minio.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
///**
// * @author Ligg
// * @create_time 2025/11/10 11:46
// * @update_time 2025/11/10 11:46
// **/
//public class FileServiceImplTest {
//    @InjectMocks
//    private FileServiceImpl fileService;
//
//    @Mock
//    private MinioConfig minioConfig;
//
//    @Mock
//    private MinioClient minioClient;
//
//    @Mock
//    private MultipartFile multipartFile;
//
//    @BeforeEach
//    void setUp() {
//        // 设置IMAGE_PATH字段的值
//        ReflectionTestUtils.setField(fileService, "IMAGE_PATH", "/test/path");
//    }
//
//    @Test
//    void testMinioFileUpload_Success() throws Exception {
//        // 准备测试数据
//        String bucketName = "test-bucket";
//        String filePath = "test-folder";
//        String fileName = "test-image.jpg";
//        String objectName = filePath + '/' + System.currentTimeMillis() + "_" + fileName;
//        String expectedUrl = "http://minio.local/test-bucket/test-folder/test-image.jpg";
//
//        // 配置mock行为
//        when(minioConfig.getBucketName()).thenReturn(bucketName);
//        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
//        when(multipartFile.getSize()).thenReturn(1024L);
//        when(multipartFile.getContentType()).thenReturn("image/jpeg");
//        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));
//
//        // 存储桶存在
//        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
//
//        // 获取预签名URL
//        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
//                .thenReturn(expectedUrl);
//
//        // 执行测试
//        String resultUrl = fileService.minioFileUpload(multipartFile, filePath);
//
//        // 验证结果
//        assertEquals(expectedUrl, resultUrl);
//
//        // 验证方法调用
//        verify(minioClient).bucketExists(any(BucketExistsArgs.class));
//        verify(minioClient).putObject(any(PutObjectArgs.class));
//        verify(minioClient).getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class));
//    }
//
//    @Test
//    void testMinioFileUpload_CreateBucketIfNotExists() throws Exception {
//        String bucketName = "new-bucket";
//        String fileName = "test-image.jpg";
//
//        when(minioConfig.getBucketName()).thenReturn(bucketName);
//        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
//        when(multipartFile.getSize()).thenReturn(1024L);
//        when(multipartFile.getContentType()).thenReturn("image/jpeg");
//        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));
//
//        // 存储桶不存在
//        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);
//
//        // 获取预签名URL
//        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
//                .thenReturn("http://minio.local/new-bucket/test-image.jpg");
//
//        // 执行测试
//        String resultUrl = fileService.minioFileUpload(multipartFile, null);
//
//        // 验证创建存储桶的方法被调用
//        verify(minioClient).makeBucket(any(MakeBucketArgs.class));
//        verify(minioClient).setBucketPolicy(any(SetBucketPolicyArgs.class));
//        verify(minioClient).putObject(any(PutObjectArgs.class));
//    }
//
//    @Test
//    void testMinioFileUpload_EmptyFilePath() throws Exception {
//        String bucketName = "test-bucket";
//        String fileName = "test-image.jpg";
//        String expectedUrl = "http://minio.local/test-bucket/test-image.jpg";
//
//        when(minioConfig.getBucketName()).thenReturn(bucketName);
//        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
//        when(multipartFile.getSize()).thenReturn(1024L);
//        when(multipartFile.getContentType()).thenReturn("image/jpeg");
//        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));
//
//        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
//        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
//                .thenReturn(expectedUrl);
//
//        // 执行测试，filePath为null
//        String resultUrl = fileService.minioFileUpload(multipartFile, null);
//        assertEquals(expectedUrl, resultUrl);
//    }
//
//    @Test
//    void testMinioFileUpload_BucketCreationFailure() throws Exception {
//        String bucketName = "test-bucket";
//        String fileName = "test-image.jpg";
//
//        when(minioConfig.getBucketName()).thenReturn(bucketName);
//        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
//
//        // 存储桶不存在且创建失败
//        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);
//        doThrow(new RuntimeException("Bucket creation failed"))
//                .when(minioClient).makeBucket(any(MakeBucketArgs.class));
//
//        // 验证抛出预期异常
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            fileService.minioFileUpload(multipartFile, "test-path");
//        });
//
//        assertEquals(BusinessStates.INTERNAL_SERVER_ERROR.getMessage(), exception.getMessage());
//    }
//
//    @Test
//    void testMinioFileUpload_FileUploadFailure() throws Exception {
//        String bucketName = "test-bucket";
//        String fileName = "test-image.jpg";
//
//        when(minioConfig.getBucketName()).thenReturn(bucketName);
//        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
//        when(multipartFile.getSize()).thenReturn(1024L);
//        when(multipartFile.getContentType()).thenReturn("image/jpeg");
//
//        // 模拟文件输入流获取失败
//        when(multipartFile.getInputStream()).thenThrow(new IOException("Failed to get input stream"));
//        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
//
//        // 执行测试 - 不应该抛出异常，方法应该处理异常并返回空字符串
//        String resultUrl = fileService.minioFileUpload(multipartFile, "test-path");
//        assertEquals("", resultUrl);
//    }
//
//    @Test
//    void testMinioFileUpload_GetUrlFailure() throws Exception {
//        String bucketName = "test-bucket";
//        String fileName = "test-image.jpg";
//
//        when(minioConfig.getBucketName()).thenReturn(bucketName);
//        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
//        when(multipartFile.getSize()).thenReturn(1024L);
//        when(multipartFile.getContentType()).thenReturn("image/jpeg");
//        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));
//
//        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
//
//        // 模拟获取URL失败
//        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
//                .thenThrow(new RuntimeException("Failed to get URL"));
//
//        // 执行测试 - 不应该抛出异常，方法应该处理异常并返回空字符串
//        String resultUrl = fileService.minioFileUpload(multipartFile, "test-path");
//        assertEquals("", resultUrl);
//    }
//
//    @Test
//    void testGetPublicReadPolicy_ReturnsCorrectPolicy() throws Exception {
//        String bucketName = "test-bucket";
//
//        // 使用反射调用私有方法
//        String policy = (String) ReflectionTestUtils.invokeMethod(fileService, "getPublicReadPolicy", bucketName);
//
//        assertNotNull(policy);
//        assertTrue(policy.contains(bucketName));
//        assertTrue(policy.contains("2025.11.10"));
//        assertTrue(policy.contains("s3:GetObject"));
//    }
//}
