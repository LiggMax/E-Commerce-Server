/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.entity.FeaturedDetailEntity;
import com.ligg.common.entity.FeaturedImageEntity;
import com.ligg.common.service.FeaturedImageService;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.DiscountUtil;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 精选商品接口
 */
@Tag(name = "客户端精选商品接口")
@RestController
@RequestMapping("/api/client/featured")
public class ClientFeaturedController {

    @Autowired
    private FeaturedService featuredService;

    @Autowired
    private FeaturedImageService featuredImageService;

    /**
     * 获取精选商品分页列表
     */
    @GetMapping
    @Operation(summary = "获取精选商品列表")
    public Response<PageVo<FeaturedVo>> pagelist(@NotNull Long pageNumber) {
        PageVo<FeaturedEntity> featuredPage = featuredService.getFeaturedPageList(pageNumber,10L);
        List<FeaturedVo> featuredVoList = featuredPage.getList().stream().map(featured -> {
            FeaturedVo featuredVo = new FeaturedVo();
            BeanUtils.copyProperties(featured, featuredVo);
            ImagesVo imagePath = ImageUtil.getImagePath(featured.getImagePath());
            featuredVo.setImages(imagePath);
            featuredVo.setDiscount(DiscountUtil.calculateDiscountPercentage(
                    featured.getOriginalPrice(),
                    featured.getCurrentPrice()).doubleValue());
            return featuredVo;
        }).toList();

        PageVo<FeaturedVo> result = new PageVo<>();
        result.setTotal(featuredPage.getTotal());
        result.setPages(featuredPage.getPages());
        result.setList(featuredVoList);
        return Response.success(BusinessStates.SUCCESS, result);
    }

    /**
     * 获取精选商品详情
     */
    @Operation(summary = "获取精选商品详情")
    @GetMapping("/detail")
    public Response<FeaturedDetailVo> getFeaturedDetail(@Schema(description = "商品id") String productId) {
        FeaturedEntity featured = featuredService.getById(productId);
        if (featured == null) {
            return Response.error(BusinessStates.NOT_FOUND);
        }
        FeaturedDetailEntity featuredDetailEntity = featuredService.getFeaturedDetailById(productId);

        FeaturedDetailVo featuredDetailVo = new FeaturedDetailVo();
        BeanUtils.copyProperties(featured, featuredDetailVo);
        //临时校验
        if (featuredDetailEntity != null) {
            featuredDetailVo.setDescription(featuredDetailEntity.getDescription());
        }
        featuredDetailVo.setImages(ImageUtil.getImagePath(featured.getImagePath()));
        featuredDetailVo.setDiscount(DiscountUtil.calculateDiscountPercentage(
                featured.getOriginalPrice(),
                featured.getCurrentPrice()
        ).doubleValue());

        List<FeaturedImageEntity> imagesList = featuredImageService.getImagesByFeaturedId(productId);
        List<FeaturedImageVo> iamgeVoList = imagesList.stream().map(images -> {
            FeaturedImageVo featuredImageVo = new FeaturedImageVo();
            BeanUtils.copyProperties(images, featuredImageVo);
            featuredImageVo.setUrl(images.getImagePath());
            return featuredImageVo;
        }).toList();
        featuredDetailVo.setDetailImages(iamgeVoList);
        return Response.success(BusinessStates.SUCCESS, featuredDetailVo);
    }
}

