package com.ligg.image_demo.controller;

import jakarta.validation.constraints.DecimalMin;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class imageController {

    private static final String IMAGE_PATH = "templates/更衣人偶坠入爱河_1756915020203.jpg";

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage() {
        Resource resource = new ClassPathResource(IMAGE_PATH);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/image/compressed")
    public ResponseEntity<StreamingResponseBody> getImageStream(
            @RequestParam(defaultValue = "0.5") double scale,
            @DecimalMin("0.1") @RequestParam(defaultValue = "0.8") float quality
    ) {
        Resource resource = new ClassPathResource(IMAGE_PATH);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        try {
            System.out.println(resource.getURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StreamingResponseBody streamingResponseBody = outputStream -> {
            try (InputStream inputStream = resource.getInputStream()) {
                Thumbnails.of(inputStream)
                        .scale(scale)
                        .outputQuality(quality)
                        .outputFormat("jpg")
                        .toOutputStream(outputStream);
            }
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(streamingResponseBody);
    }
}
