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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private FileService fileService;

    /**
     * 上传轮播图数据
     */
    @Operation(summary = "上传轮播图数据")
    @PostMapping
    public Response<String> upload(@Validated CarouselDto carousel,
                                   MultipartFile imageFile) {
        String imagePath = fileService.uploadImage(imageFile,"/Carousel");
        if (imageFile.getSize() > 1024 * 1024 * 2) {
            return Response.error(BusinessStates.BAD_REQUEST);
        }

        CarouselEntity entity = new CarouselEntity();
        entity.setCreatedAt(LocalDateTime.now());
        BeanUtils.copyProperties(carousel, entity);
        if (!imagePath.isEmpty()) {
            entity.setImagePath(imagePath);
        }

        int res = carouselService.save(entity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 获取轮播图数据
     */
    @Operation(summary = "获取轮播图数据")
    @GetMapping
    public Response<PageVo<CarouselEntity>> getCarousel(Long pageNumber, Long pageSize) {
        PageVo<CarouselEntity> carouselPage = carouselService.getCarouselPage(pageNumber,pageSize);
        return Response.success(BusinessStates.SUCCESS,carouselPage);
    }
}
