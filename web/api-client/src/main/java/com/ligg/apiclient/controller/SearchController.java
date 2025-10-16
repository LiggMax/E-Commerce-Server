/**
 * @Author Ligg
 * @Time 2025/10/8
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.SearchService;
import com.ligg.common.utils.DiscountUtil;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.module.vo.search.SearchVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 搜索接口
 */
@Tag(name = "客户端搜索接口")
@RestController
@RequestMapping("/api/client/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 搜索商品
     *
     * @param keyword    关键字
     * @param pageNumber 页码
     * @param sort       排序(1: 默认排序 2:价格低到高 3:价格高到低 )
     * @return 商品列表
     */
    @GetMapping
    public Response<PageVo<SearchVo>> searchCommodity(@RequestParam @NotNull String keyword,
                                                      @RequestParam(defaultValue = "1") Long pageNumber,
                                                      @RequestParam(defaultValue = "1") Integer sort) {
        PageVo<ProductEntity> pageVo = searchService.searchCommodityPageList(keyword, pageNumber, 20L, sort);
        List<SearchVo> pageVolist = pageVo.getList().stream().map(product -> {
            SearchVo searchVo = new SearchVo();
            BeanUtils.copyProperties(product, searchVo);
            searchVo.setUrl(ImageUtil.getImagePath(product.getImagePath()));
            searchVo.setDiscount(DiscountUtil.calculateDiscountPercentage(product.getOriginalPrice(), product.getCurrentPrice()).doubleValue());
            return searchVo;
        }).toList();
        PageVo<SearchVo> searchData = new PageVo<>();
        searchData.setPages(pageVo.getPages());
        searchData.setTotal(pageVo.getTotal());
        searchData.setList(pageVolist);
        return Response.success(BusinessStates.SUCCESS, searchData);
    }
}
