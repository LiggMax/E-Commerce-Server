package com.ligg.dto.admin;

import com.ligg.entity.admin.CarouselEntity;
import org.springframework.web.multipart.MultipartFile;

public class CarouselDto extends CarouselEntity {
    private MultipartFile image;
}
