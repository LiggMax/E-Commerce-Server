package com.ligg.apicommon.controller;

import com.ligg.common.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class imageController {

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    @Autowired
    private FileService fileService;

    /**
     * 获取图片资源
     *
     * @param imageName
     * @param date
     * @return
     */
    @GetMapping("/image/{path}/{date}/{imageName}")
    public ResponseEntity<StreamingResponseBody> getImageStream(
            @PathVariable String date,
            @PathVariable String path,
            @PathVariable String imageName
    ) {
        Path imagePath = Paths.get(IMAGE_PATH, path, date, imageName);
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return ResponseEntity.ok().headers(headers).body(fileService.getImageInputStream(path, date, imageName));
    }

    /**
     * 获取多个类型图片资源
     *
     * @param date      图片日期
     * @param imageName 图片名称
     * @return 图片内容
     */
    @GetMapping("/image/{type}/{path}/{date}/{imageName}")
    public ResponseEntity<StreamingResponseBody> getImageStream(
            @PathVariable String type,
            @PathVariable String path,
            @PathVariable String date,
            @PathVariable String imageName
    ) {

        Path imagePath = Paths.get(IMAGE_PATH, path, date, imageName);
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        StreamingResponseBody imageInputStream = fileService.getImageInputStream(path, type, date, imageName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

        return ResponseEntity.ok().headers(headers).body(imageInputStream);
    }

}
