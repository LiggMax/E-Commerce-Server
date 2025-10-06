/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.entity.FeaturedDetailEntity;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.DiscountUtil;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.FeaturedDetailVo;
import com.ligg.common.vo.FeaturedVo;
import com.ligg.common.vo.ImagesVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private ImageUtil imageUtil;

    /**
     * 获取精选商品列表
     */
    @GetMapping
    @Operation(summary = "获取精选商品列表")
    public Response<List<FeaturedVo>> list() {
        List<FeaturedEntity> featuredList = featuredService.list();
        List<FeaturedVo> featuredVoList = featuredList.stream().map(featured -> {
            FeaturedVo featuredVo = new FeaturedVo();
            BeanUtils.copyProperties(featured, featuredVo);
            ImagesVo imagePath = imageUtil.getImagePath(featured.getImagePath());
            featuredVo.setImages(imagePath);
            featuredVo.setDiscount(DiscountUtil.calculateDiscountPercentage(
                    featured.getOriginalPrice(),
                    featured.getCurrentPrice()).doubleValue());
            return featuredVo;
        }).toList();
        return Response.success(BusinessStates.SUCCESS, featuredVoList);
    }

    /**
     * 获取精选商品详情
     */
    //TODO 完善详情接口
    @Operation(summary = "获取精选商品详情")
    @GetMapping("/detail")
    public Response<FeaturedDetailVo> getFeaturedDetail(@Schema(description = "商品id") Long productId) {
        FeaturedEntity featured = featuredService.getById(productId);
        if (featured == null) {
            return Response.error(BusinessStates.NOT_FOUND);
        }
        FeaturedDetailEntity ProductDetail = featuredService.getProductDetailById(productId);
        FeaturedDetailVo featuredDetail = new FeaturedDetailVo();
        BeanUtils.copyProperties(featured, featuredDetail);
        featuredDetail.setDescription(ProductDetail.getDescription());
        featuredDetail.setImages(imageUtil.getImagePath(featured.getImagePath()));
        featuredDetail.setDiscount(DiscountUtil.calculateDiscountPercentage(
                featured.getOriginalPrice(),
                featured.getCurrentPrice()
        ).doubleValue());
        return Response.success(BusinessStates.SUCCESS, featuredDetail);
    }
}

