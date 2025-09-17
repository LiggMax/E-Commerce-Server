package com.ligg.image_demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class ImageDemoApplicationTests {

    @Test
    void contextLoads() {
        File video = new File("templates/video/bl01z.mp4");
        System.out.println(video.getPath());

    }

}
