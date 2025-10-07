/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.constants.Constant;
import com.ligg.common.dto.FeaturedDto;
import com.ligg.common.entity.FeaturedDetailEntity;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.entity.FeaturedImageEntity;
import com.ligg.common.service.FeaturedDetailService;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.service.FileService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.FeaturedImageService;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.FeaturedDetailVo;
import com.ligg.common.vo.FeaturedImageVo;
import com.ligg.common.vo.PageVo;
import com.ligg.common.vo.search.FeaturedSearchVo;
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
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 精选商品接口
 */
@Tag(name = "精选商品接口")
@RestController
@RequestMapping("/api/admin/featured")
public class AdminFeaturedController {

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private FileService fileService;

    @Autowired
    private FeaturedService featuredService;

    @Autowired
    private FeaturedImageService featuredImageService;

    @Autowired
    private FeaturedDetailService featuredDetailService;

    /**
     * 上传精选产品数据
     */
    @PostMapping
    @Operation(summary = "上传精选产品数据")
    public Response<String> uploadFeatured(@Validated FeaturedDto featured, @Schema(description = "图片文件") @NotNull(message = "图片文件不能为空") MultipartFile imageFile) {
        String imagePath = fileService.uploadImage(imageFile, Constant.FEATURED_FILE_PATH);
        FeaturedEntity featuredEntity = new FeaturedEntity();
        BeanUtils.copyProperties(featured, featuredEntity);
        featuredEntity.setRating(new Random().nextInt(5, 11));
        featuredEntity.setImagePath(imagePath);
        featuredEntity.setCreatedAt(LocalDateTime.now());
        featuredEntity.setUpdateAt(LocalDateTime.now());
        featuredService.saveFeatured(featuredEntity);

        //保存详情数据
        FeaturedDetailEntity detailEntity = new FeaturedDetailEntity();
        detailEntity.setDescription(featured.getDescription());
        detailEntity.setFeaturedId(featuredEntity.getId());
        featuredDetailService.save(detailEntity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 编辑精选产品数据
     */
    @PutMapping
    @Operation(summary = "编辑精选产品数据")
    public Response<String> updateFeatured(FeaturedDto featured, @Schema(description = "图片文件") MultipartFile imageFile) {
        FeaturedEntity featuredEntity = new FeaturedEntity();
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 1024 * 1024 * 2) {
                return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
            }
            String imagePath = fileService.uploadImage(imageFile, Constant.FEATURED_FILE_PATH);

            //上传封面成功后异步删除旧图片
            if (imagePath != null) {
                FeaturedEntity featuredData = featuredService.getById(featured.getId());
                String dataImagePath = IMAGE_PATH + featuredData.getImagePath().replace("/api/image", "");
                fileService.deleteFileAsync(dataImagePath);
            }
            featuredEntity.setImagePath(imagePath);
        }
        BeanUtils.copyProperties(featured, featuredEntity);
        featuredEntity.setUpdateAt(LocalDateTime.now());
        featuredService.updateById(featuredEntity);
        //featuredService.updateFeaturedById(featuredEntity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 获取精选商品列表
     */
    @Operation(summary = "获取精选商品列表")
    @GetMapping
    public Response<PageVo<FeaturedDetailVo>> getFeatured(@Schema(description = "页码") Long pageNumber, @Schema(description = "每页数量") Long pageSize) {
        PageVo<FeaturedSearchVo> featuredList = featuredService.Pagelist(pageNumber, pageSize);
        PageVo<FeaturedDetailVo> pageVo = new PageVo<>();
        pageVo.setPages(featuredList.getPages());
        pageVo.setTotal(featuredList.getTotal());
        pageVo.setList(featuredList.getList().stream().map(featured -> {
            FeaturedDetailVo featuredVo = new FeaturedDetailVo();
            BeanUtils.copyProperties(featured, featuredVo);
            featuredVo.setImages(imageUtil.getImagePath(featured.getImagePath()));
            return featuredVo;
        }).collect(Collectors.toList()));
        return Response.success(BusinessStates.SUCCESS, pageVo);
    }

    /**
     * 删除精选商品
     */
    @Operation(summary = "删除精选商品")
    @DeleteMapping("/{id}")
    public Response<String> deleteFeatured(@Schema(description = "商品id") @PathVariable Long id) {
        FeaturedEntity featured = featuredService.getById(id);
        if (featured == null) {
            return Response.error(BusinessStates.NOT_FOUND);
        }
        String imagePath = IMAGE_PATH + featured.getImagePath().replace("/api/image", "");
        if (featuredService.removeById(id)) {
            //异步删除
            fileService.deleteFileAsync(imagePath);
            return Response.success(BusinessStates.SUCCESS);
        }
        return Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 上传张图片
     */
    @Operation(summary = "上传详情页图片(可多张)")
    @PostMapping("/image")
    public Response<String> uploadImages(@NotNull String featuredId, @NotNull MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty() || imageFile.getSize() > Constant.FILE_SIZE) {
            return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
        }
        String imagePath = fileService.uploadImage(imageFile, Constant.FEATURED_FILE_PATH);
        if (StringUtils.hasText(imagePath)) {
            FeaturedImageEntity featuredImage = new FeaturedImageEntity();
            featuredImage.setFeaturedId(featuredId);
            featuredImage.setImagePath(imagePath);
            //TODO 临时添加
            featuredImage.setSort(0);
            return featuredImageService.save(featuredImage)
                    ? Response.success(BusinessStates.SUCCESS, "上传成功")
                    : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
        }
        return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
    }

    /**
     * 获取上传的图片
     */
    @Operation(summary = "获取上传的图片")
    @GetMapping("/image")
    public Response<List<FeaturedImageVo>> getImages(@Schema(description = "精选商品id") @NotNull String featuredId) {
        List<FeaturedImageVo> featuredImageList = featuredImageService.getList(featuredId);
        return Response.success(BusinessStates.SUCCESS, featuredImageList);
    }
}
