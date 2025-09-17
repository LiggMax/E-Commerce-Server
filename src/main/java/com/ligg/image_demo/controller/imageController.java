package com.ligg.image_demo.controller;

import jakarta.validation.constraints.DecimalMin;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class imageController {

    private static final String IMAGE_PATH = "templates/";
    private static final String VIDEO_PATH = "templates/video/";

    @GetMapping("/video/{name}")
    public ResponseEntity<StreamingResponseBody> getVideo(@PathVariable String name) throws IOException {
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
        return ResponseEntity.ok()
                .headers(headers)
                .body(streamingResponseBody);
    }

    @GetMapping("/image/compressed/{imageName}")
    public ResponseEntity<StreamingResponseBody> getImageStream(
            @RequestParam(defaultValue = "0.8") double scale,
            @DecimalMin("0.1") @RequestParam(defaultValue = "0.8") float quality,
            @PathVariable String imageName) {
        Resource resource = new ClassPathResource(IMAGE_PATH + imageName);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
//        try {
//            System.out.println(resource.getURL());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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
