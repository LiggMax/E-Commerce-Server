/**
 * @Author Ligg
 * @Time 2025/9/23
 * <p>
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.constants.Constant;
import com.ligg.common.dto.CarouselDto;
import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.enums.CarouselStatus;
import com.ligg.common.service.CarouselService;
import com.ligg.common.service.FileService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.CarouselVo;
import com.ligg.common.vo.PageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private ImageUtil imageUtil;

    /**
     * 获取轮播图数据
     */
    @Operation(summary = "获取轮播图数据")
    @GetMapping
    public Response<PageVo<CarouselVo>> getCarousel(Long pageNumber, Long pageSize) {
        PageVo<CarouselEntity> carouselPage = carouselService.getCarouselPage(pageNumber, pageSize);

        //VO转换
        PageVo<CarouselVo> carouselVoPage = new PageVo<>();
        carouselVoPage.setPages(carouselPage.getPages());
        carouselVoPage.setTotal(carouselPage.getTotal());

        List<CarouselVo> carouselList = carouselPage.getList().stream().map(entity -> {
            CarouselVo carouselVo = new CarouselVo();
            BeanUtils.copyProperties(entity, carouselVo);
            carouselVo.setImages(imageUtil.getImagePath(entity.getImagePath()));
            carouselVo.setStatus(entity.getStatus().isEnabled());
            return carouselVo;
        }).toList();
        carouselVoPage.setList(carouselList);
        return Response.success(BusinessStates.SUCCESS, carouselVoPage);
    }

    /**
     * 上传轮播图数据
     */
    @PostMapping
    @Operation(summary = "上传轮播图数据")
    public Response<String> upload(@Validated CarouselDto carousel,
                                   @Schema(description = "图片文件") MultipartFile imageFile) {
        String imagePath = fileService.uploadImage(imageFile, Constant.CAROUSEL_FILE_PATH);
        if (imageFile.getSize() > 1024 * 1024 * 2) {
            return Response.error(BusinessStates.BAD_REQUEST);
        }

        CarouselEntity entity = new CarouselEntity();
        entity.setStatus(CarouselStatus.fromBoolean(carousel.getStatus()));
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
                                   @RequestParam(required = false) @Schema(description = "图片文件") MultipartFile imageFile) {
        CarouselEntity carouselEntity = new CarouselEntity();
        BeanUtils.copyProperties(carousel, carouselEntity);
        carouselEntity.setStatus(CarouselStatus.fromBoolean(carousel.getStatus()));
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 1024 * 1024 * 2) {
                return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
            }
            String imagePath = fileService.uploadImage(imageFile, Constant.CAROUSEL_FILE_PATH);
            if (StringUtils.hasText(imagePath)) {
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
     * 更新轮播图状态
     */
    @Operation(summary = "更新轮播图状态")
    @PatchMapping("/status")
    public Response<String> updateStatus(@NotNull Integer id, @NotNull Boolean status) {
        CarouselEntity entity = new CarouselEntity();
        entity.setId(id);
        entity.setStatus(CarouselStatus.fromBoolean(status));
        entity.setUpdateAt(LocalDateTime.now());
        return carouselService.updateById(entity) ?
                Response.success(BusinessStates.SUCCESS) :
                Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 更新轮播图排序
     */
    @Operation(summary = "更新轮播图排序")
    @PatchMapping("/sort")
    public Response<String> updateSort(@RequestBody CarouselDto carousel) {
        CarouselEntity entity = new CarouselEntity();
        entity.setId(carousel.getId());
        entity.setSort(carousel.getSort());
        entity.setUpdateAt(LocalDateTime.now());
        return carouselService.updateById(entity) ?
                Response.success(BusinessStates.SUCCESS) :
                Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除轮播图
     */
    @Operation(summary = "删除轮播图")
    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable String id) {
        CarouselEntity entity = carouselService.getById(id);
        if (entity == null) {
            return Response.error(BusinessStates.NOT_FOUND);
        }
        if (carouselService.removeById(id)) {
            String imagePath = IMAGE_PATH + entity.getImagePath().replace("/api/image", "");
            fileService.deleteFileAsync(imagePath);
            return Response.success(BusinessStates.SUCCESS);
        }
        return Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }
}
