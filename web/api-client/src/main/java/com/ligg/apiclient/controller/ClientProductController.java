/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
package com.ligg.apiclient.controller;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.ligg.apiclient.pojo.vo.TagVo;
import com.ligg.common.enums.SearchSorting;
import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.module.entity.ProductDetailEntity;
import com.ligg.common.module.entity.ProductImageEntity;
import com.ligg.common.module.vo.*;
import com.ligg.common.module.vo.search.SearchVo;
import com.ligg.common.service.product.ProductCommentService;
import com.ligg.common.service.product.ProductImageService;
import com.ligg.common.service.product.ProductService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.SearchService;
import com.ligg.common.service.SpecService;
import com.ligg.common.utils.DiscountUtil;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 商品接口
 */
@Tag(name = "商品接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/product")
public class ClientProductController {

    private final SpecService specService;

    private final SearchService searchService;

    private final ProductService productService;

    private final ProductCommentService commentService;

    private final ProductImageService productImageService;

    /**
     * 获取商品分页列表
     */
    @GetMapping
    @Operation(summary = "获取商品列表")
    public Response<PageVo<ProductVo>> pagelist(@Validated @NotNull(message = "页码不能为空") Long pageNumber) {
        PageVo<ProductEntity> featuredPage = productService.getFeaturedPageList(pageNumber, 10L);
        List<ProductVo> featuredVoList = featuredPage.getList().stream().map(featured -> {
            ProductVo featuredVo = new ProductVo();
            BeanUtils.copyProperties(featured, featuredVo);
            ImagesVo imagePath = ImageUtil.getImagePath(featured.getImagePath());
            featuredVo.setImages(imagePath);
            featuredVo.setDiscount(DiscountUtil.calculateDiscountPercentage(
                    featured.getOriginalPrice(),
                    featured.getCurrentPrice()).doubleValue());
            return featuredVo;
        }).toList();

        PageVo<ProductVo> result = new PageVo<>();
        result.setTotal(featuredPage.getTotal());
        result.setPages(featuredPage.getPages());
        result.setList(featuredVoList);
        return Response.success(BusinessStates.SUCCESS, result);
    }

    /**
     * 获取商品详情
     */
    @Operation(summary = "获取商品详情")
    @GetMapping("/detail")
    public Response<ProductDetailVo> getFeaturedDetail(@Schema(description = "商品id") String productId) {
        ProductEntity featured = productService.getById(productId);
        if (featured == null) {
            return Response.error(BusinessStates.NOT_FOUND);
        }

        //获取基本详情信息
        ProductDetailEntity featuredDetailEntity = productService.getFeaturedDetailById(productId);
        ProductDetailVo featuredDetailVo = new ProductDetailVo();
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

        //获取详情图片
        List<ProductImageEntity> imagesList = productImageService.getImagesByFeaturedId(productId);
        List<ProductImageVo> iamgeVoList = imagesList.stream().map(images -> {
            ProductImageVo featuredImageVo = new ProductImageVo();
            BeanUtils.copyProperties(images, featuredImageVo);
            featuredImageVo.setUrl(images.getImagePath());
            return featuredImageVo;
        }).toList();
        featuredDetailVo.setDetailImages(iamgeVoList);

        //获取规格信息
        List<SpecVo> specVoList = specService.getSpecDetailByProductId(productId);
        featuredDetailVo.setSpecs(specVoList);

        //阅览数++
        productService.lambdaUpdate()
                .eq(ProductEntity::getId, productId)
                .setIncrBy(ProductEntity::getViews, ThreadLocalRandom.current().nextInt(10,21))
                .update();
        return Response.success(BusinessStates.SUCCESS, featuredDetailVo);
    }

    /**
     * 商品搜索
     */
    @GetMapping("/search")
    @Operation(summary = "商品搜索")
    public Response<PageVo<SearchVo>> search(@Schema(description = "关键字") @NotNull String keyword,
                                             @Schema(description = "页码") @NotNull Long pageNumber,
                                             @Schema(description = "排序") @RequestParam(required = false) SearchSorting sort) {
        //获取商品分页列表
        PageVo<ProductEntity> searchData = searchService.searchCommodityPageList(keyword, pageNumber, 20L, sort);
        List<SearchVo> searchResult = searchData.getList().stream().map(search -> {
            SearchVo searchVo = new SearchVo();
            BeanUtils.copyProperties(search, searchVo);
            searchVo.setUrl(ImageUtil.getImagePath(search.getImagePath()));
            searchVo.setDiscount(DiscountUtil.calculateDiscountPercentage(
                    search.getOriginalPrice(),
                    search.getCurrentPrice()).doubleValue());
            return searchVo;
        }).toList();

        PageVo<SearchVo> pageVo = new PageVo<>();
        pageVo.setPages(searchData.getPages());
        pageVo.setTotal(searchData.getTotal());
        pageVo.setList(searchResult);
        return Response.success(BusinessStates.SUCCESS, pageVo);
    }

    /**
     * 获取商品评论
     */
    @GetMapping("/comment")
    @Operation(summary = "获取商品评论")
    public Response<PageVo<ProductCommentVo>> getComment(@Schema(description = "商品id") @NotNull Long productId,
                                                         @NotNull @Max(100) Long pageNumber,
                                                         @NotNull @Min(5) @Max(20) Long pageSize
    ) {
        PageVo<ProductCommentVo> commentPage = commentService.getCommentByProductId(productId, pageNumber, pageSize);
        return Response.success(BusinessStates.SUCCESS, commentPage);
    }

    /**
     * 获取首页标签
     */
    @GetMapping("/tag")
    @Operation(summary = "获取首页标签")
    public Response<List<TagVo>> getTagList() {
        LambdaQueryChainWrapper<ProductEntity> last = productService.lambdaQuery().select().last("limit 4");
        return Response.success(BusinessStates.SUCCESS, last.list().stream().map(product -> {
            TagVo tagVo = new TagVo();
            tagVo.setTagName(product.getTitle());
            return tagVo;
        }).toList());
    }
}

