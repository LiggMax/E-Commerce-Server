package com.ligg.ecommerceclient.controller;

import com.ligg.Imagenum.ImageType;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/image/{type}/{imageName}")
    public ResponseEntity<StreamingResponseBody> getImageStream(
            @PathVariable String imageName, @PathVariable String type) {
        Resource resource = new ClassPathResource(IMAGE_PATH + imageName);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        ImageType imageType;
        try {
            imageType = ImageType.valueOf(type.toLowerCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        StreamingResponseBody streamingResponseBody = outputStream -> {
            try (InputStream inputStream = resource.getInputStream()) {
                Thumbnails.of(inputStream)
                        .scale(imageType.getScale())
                        .outputQuality(imageType.getQuality())
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

    @GetMapping("/image/types")
    public ResponseEntity<Map<String, String>> getImageTypes() {
        Map<String, String> imageTypes = new HashMap<>();
        String baseUrl = "http://192.168.124.3:8080/api/image/";

        Arrays.stream(ImageType.values()).forEach(type -> {
            imageTypes.put(type.name(), baseUrl + type.name() + "/更衣人偶_1756915020203.jpg");
        });

        return ResponseEntity.ok(imageTypes);
    }
}
