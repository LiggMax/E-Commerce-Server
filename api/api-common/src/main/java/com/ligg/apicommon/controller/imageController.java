package com.ligg.apicommon.controller;

import com.ligg.common.Imagenum.ImageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class imageController {

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    private static final String VIDEO_PATH = "templates/video/";

    @GetMapping("/video/{name}")
    public ResponseEntity<StreamingResponseBody> getVideo(@PathVariable String name) {
        Resource resource = new ClassPathResource(VIDEO_PATH + name);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        StreamingResponseBody streamingResponseBody = outputStream -> {
            try (InputStream inputStream = resource.getInputStream()) {
                inputStream.transferTo(outputStream);
            }
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
        return ResponseEntity.ok().headers(headers).body(streamingResponseBody);
    }

//    @GetMapping("/image/{type}/{imageName}")
//    public ResponseEntity<StreamingResponseBody> getImageStream(
//            @PathVariable String imageName, @PathVariable String type) {
//        Resource resource = new ClassPathResource(IMAGE_PATH + imageName);
//        if (!resource.exists()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        ImageType imageType;
//        try {
//            imageType = ImageType.valueOf(type.toLowerCase());
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
//        StreamingResponseBody streamingResponseBody = outputStream -> {
//            try (InputStream inputStream = resource.getInputStream()) {
//                Thumbnails.of(inputStream)
//                        .scale(imageType.getScale())
//                        .outputQuality(imageType.getQuality())
//                        .outputFormat("jpg")
//                        .toOutputStream(outputStream);
//            }
//        };
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(streamingResponseBody);
//    }

    @GetMapping("/image/types")
    public ResponseEntity<Map<String, String>> getImageTypes() {
        Map<String, String> imageTypes = new HashMap<>();
        String baseUrl = "http://192.168.124.3:8080/api/image/";

        Arrays.stream(ImageType.values()).forEach(type -> imageTypes.put(type.name(), baseUrl + type.name() + "/更衣人偶_1756915020203.jpg"));

        return ResponseEntity.ok(imageTypes);
    }

    /**
     * 获取图片资源
     *
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片内容
     */
    @GetMapping("/image/{date}/{imageName}")
    public ResponseEntity<StreamingResponseBody> getImageStream(@PathVariable String date, @PathVariable String imageName) {

        Path imagePath = Paths.get(IMAGE_PATH, date, imageName);
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        // 创建流，将图片内容写入响应体
        StreamingResponseBody streamingResponseBody = outputStream -> {
            try (InputStream inputStream = Files.newInputStream(imagePath)) {
                inputStream.transferTo(outputStream);
            }
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

        return ResponseEntity.ok().headers(headers).body(streamingResponseBody);
    }

}
