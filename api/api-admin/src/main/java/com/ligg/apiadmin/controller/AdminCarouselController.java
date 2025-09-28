/**
 * @Author Ligg
 * @Time 2025/9/23
 * <p>
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.dto.CarouselDto;
import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.service.CarouselService;
import com.ligg.common.service.FileService;
import com.ligg.common.statuEnum.BusinessStates;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.PageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * 轮播图接口
 */
@Tag(name = "轮播图接口")
@RestController
@RequestMapping("/api/admin/carousel")
public class AdminCarouselController {

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private FileService fileService;

    /**
     * 上传轮播图数据
     */
    @PostMapping
    @Operation(summary = "上传轮播图数据")
    public Response<String> upload(@Validated CarouselDto carousel,
                                   @Schema(description = "图片文件") MultipartFile imageFile) {
        String imagePath = fileService.uploadImage(imageFile, "/Carousel");
        if (imageFile.getSize() > 1024 * 1024 * 2) {
            return Response.error(BusinessStates.BAD_REQUEST);
        }

        CarouselEntity entity = new CarouselEntity();
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdateAt(LocalDateTime.now());
        BeanUtils.copyProperties(carousel, entity);
        if (!imagePath.isEmpty()) {
            entity.setImagePath(imagePath);
        }
        return carouselService.save(entity) ?
                Response.success(BusinessStates.SUCCESS) :
                Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 编辑轮播图数据
     */
    @Operation(summary = "编辑轮播图")
    @PutMapping
    public Response<String> update(@Validated CarouselDto carousel,
                                   @Schema(description = "图片文件") MultipartFile imageFile) {
        CarouselEntity carouselEntity = new CarouselEntity();
        BeanUtils.copyProperties(carousel, carouselEntity);
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 1024 * 1024 * 2) {
                return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
            }
            String imagePath = fileService.uploadImage(imageFile, "/Carousel");
            if (imagePath != null && !imagePath.isEmpty()) {
                CarouselEntity entity = carouselService.getById(carouselEntity.getId());
                String oldImagePath = IMAGE_PATH + entity.getImagePath().replace("/api/image", "");
                fileService.deleteFileAsync(oldImagePath);
            }
            carouselEntity.setImagePath(imagePath);
        }
        carouselEntity.setUpdateAt(LocalDateTime.now());
        return carouselService.updateById(carouselEntity) ?
                Response.success(BusinessStates.SUCCESS) :
                Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 获取轮播图数据
     */
    @Operation(summary = "获取轮播图数据")
    @GetMapping
    public Response<PageVo<CarouselEntity>> getCarousel(Long pageNumber, Long pageSize) {
        PageVo<CarouselEntity> carouselPage = carouselService.getCarouselPage(pageNumber, pageSize);
        return Response.success(BusinessStates.SUCCESS, carouselPage);
    }
}
