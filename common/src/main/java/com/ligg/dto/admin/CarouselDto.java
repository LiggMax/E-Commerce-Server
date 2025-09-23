package com.ligg.dto.admin;

import com.ligg.entity.admin.CarouselEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "轮播图数据")
public class CarouselDto extends CarouselEntity {
    private MultipartFile image;
}
