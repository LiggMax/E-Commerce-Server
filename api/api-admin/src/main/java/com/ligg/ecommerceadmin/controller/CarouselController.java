package com.ligg.ecommerceadmin.controller;

import com.ligg.dto.admin.CarouselDto;
import com.ligg.ecommerceadmin.service.CarouselService;
import com.ligg.entity.admin.CarouselEntity;
import com.ligg.service.FileService;
import com.ligg.statuEnum.BusinessStates;
import com.ligg.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Ligg
 * @Time 2025/9/23
 * <p>
 * 轮播图控制器
 **/
@Tag(name = "轮播图接口")
@RestController
@RequestMapping("/api/admin/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private FileService fileService;

    /**
     * 上传轮播图数据
     */
    @Operation(summary = "上传轮播图数据")
    @PostMapping("/upload")
    public Response<String> upload(CarouselDto carousel,
                                   MultipartFile imageFile) {
        String imagePath = fileService.uploadImage(imageFile);
        if (imageFile.getSize() > 1024 * 1024 * 2) {
            return Response.error(BusinessStates.BAD_REQUEST);
        }

        CarouselEntity entity = new CarouselEntity();
        entity.setTitle(carousel.getTitle());
        entity.setSubtitle(carousel.getSubtitle());
        entity.setDescription(carousel.getDescription());
        entity.setStatus(carousel.getStatus());
        entity.setTarget(carousel.getTarget());
        entity.setSort(carousel.getSort());
        entity.setLink(carousel.getLink());
        entity.setButtonText(carousel.getButtonText());
        if (!imagePath.isEmpty()) {
            entity.setImagePath(imagePath);
        }

        int res = carouselService.upload(entity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 上传轮播图图片
     */
    @PostMapping("/file")
    public Response<String> uploadFile(MultipartFile file) {
        String url = fileService.uploadImage(file);
        return Response.success(BusinessStates.SUCCESS, url);
    }
}
