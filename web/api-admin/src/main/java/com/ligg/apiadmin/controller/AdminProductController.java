/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.constants.Constant;
import com.ligg.common.module.dto.ProductDto;
import com.ligg.common.module.entity.ProductDetailEntity;
import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.module.entity.ProductImageEntity;
import com.ligg.common.service.ProductDetailService;
import com.ligg.common.service.ProductService;
import com.ligg.common.service.FileService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.ProductImageService;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.module.vo.ProductDetailVo;
import com.ligg.common.module.vo.ProductImageVo;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.module.vo.search.FeaturedSearchVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
 * 商品接口
 */
@Validated
@Tag(name = "商品接口")
@RestController
@RequestMapping("/api/admin/featured")
@RequiredArgsConstructor
public class AdminProductController {

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;
    private final FileService fileService;
    private final ProductService productService;
    private final ProductImageService productImageService;
    private final ProductDetailService productDetailService;

    /**
     * 上传商品数据
     */
    @PostMapping
    @Operation(summary = "上传精选产品数据")
    public Response<String> uploadFeatured(@Validated ProductDto product,
                                           @Schema(description = "图片文件") @NotNull(message = "图片文件不能为空") MultipartFile imageFile) {
        //保存基本数据
        String imagePath = fileService.uploadImage(imageFile, Constant.FEATURED_FILE_PATH);
        ProductEntity featuredEntity = new ProductEntity();
        BeanUtils.copyProperties(product, featuredEntity);
        featuredEntity.setRating(new Random().nextInt(5, 11));
        featuredEntity.setImagePath(imagePath);
        featuredEntity.setCreatedAt(LocalDateTime.now());
        featuredEntity.setUpdateAt(LocalDateTime.now());
        productService.saveFeatured(featuredEntity);

        //保存详情数据
        ProductDetailEntity detailEntity = new ProductDetailEntity();
        detailEntity.setDescription(product.getDescription());
        detailEntity.setProductId(featuredEntity.getId());
        productDetailService.save(detailEntity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 编辑商品数据
     */
    @PutMapping
    @Operation(summary = "编辑商品数据")
    public Response<String> updateFeatured(ProductDto featured, @Schema(description = "图片文件") MultipartFile imageFile) {
        ProductEntity featuredEntity = new ProductEntity();
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 1024 * 1024 * 2) {
                return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
            }
            String imagePath = fileService.uploadImage(imageFile, Constant.FEATURED_FILE_PATH);

            //上传封面成功后异步删除旧图片
            if (imagePath != null) {
                ProductEntity featuredData = productService.getById(featured.getId());
                String dataImagePath = IMAGE_PATH + featuredData.getImagePath().replace("/api/image", "");
                fileService.deleteFileAsync(dataImagePath);
            }
            featuredEntity.setImagePath(imagePath);
        }
        BeanUtils.copyProperties(featured, featuredEntity);
        featuredEntity.setUpdateAt(LocalDateTime.now());
        productService.updateById(featuredEntity);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 获取商品列表
     */
    @Operation(summary = "获取商品列表")
    @GetMapping
    public Response<PageVo<ProductDetailVo>> getFeatured(@Schema(description = "页码") Long pageNumber, @Schema(description = "每页数量") Long pageSize) {
        PageVo<FeaturedSearchVo> featuredList = productService.getProductDetailPagelist(pageNumber, pageSize);
        PageVo<ProductDetailVo> pageVo = new PageVo<>();
        pageVo.setPages(featuredList.getPages());
        pageVo.setTotal(featuredList.getTotal());
        pageVo.setList(featuredList.getList().stream().map(featured -> {
            ProductDetailVo featuredVo = new ProductDetailVo();
            BeanUtils.copyProperties(featured, featuredVo);
            featuredVo.setImages(ImageUtil.getImagePath(featured.getImagePath()));
            return featuredVo;
        }).collect(Collectors.toList()));
        return Response.success(BusinessStates.SUCCESS, pageVo);
    }

    /**
     * 删除商品
     */
    @Operation(summary = "删除商品")
    @DeleteMapping("/{id}")
    public Response<String> deleteFeatured(@Schema(description = "商品id") @PathVariable Long id) {
        ProductEntity featured = productService.getById(id);
        if (featured == null) {
            return Response.error(BusinessStates.NOT_FOUND);
        }
        String imagePath = IMAGE_PATH + featured.getImagePath().replace("/api/image", "");
        if (productService.removeById(id)) {
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
    public Response<String> uploadImages(@Schema(description = "精选商品id") @NotNull String featuredId,
                                         @Schema(description = "图片文件") @NotNull MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty() || imageFile.getSize() > Constant.FILE_SIZE) {
            return Response.error(BusinessStates.FILE_UPLOAD_FAILED);
        }
        List<ProductImageEntity> imageList = productImageService.getList(featuredId);
        if (imageList.size() >= 6) {
            return Response.error(BusinessStates.BAD_REQUEST, "图片文件超出");
        }
        String imagePath = fileService.uploadImage(imageFile, Constant.FEATURED_FILE_PATH);
        if (StringUtils.hasText(imagePath)) {
            ProductImageEntity featuredImage = new ProductImageEntity();
            featuredImage.setProductId(featuredId);
            featuredImage.setImagePath(imagePath);
            // 临时添加
            featuredImage.setSort(0);
            return productImageService.save(featuredImage)
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
    public Response<List<ProductImageVo>> getImages(@Schema(description = "精选商品id") @NotNull String productId) {
        List<ProductImageEntity> featuredImageList = productImageService.getList(productId);
        return Response.success(BusinessStates.SUCCESS, featuredImageList.stream().map(featuredImage -> {
            ProductImageVo imageVo = new ProductImageVo();
            imageVo.setId(featuredImage.getId());
            imageVo.setSort(featuredImage.getSort());
            imageVo.setUrl(featuredImage.getImagePath());
            return imageVo;
        }).toList());
    }
}
