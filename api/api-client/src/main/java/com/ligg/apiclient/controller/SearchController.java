/**
 * @Author Ligg
 * @Time 2025/10/8
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.service.SearchService;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.ImagesVo;
import com.ligg.common.vo.PageVo;
import com.ligg.common.vo.search.SearchVo;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class SearchController {

    @Autowired
    private SearchService searchService;


    /**
     * 搜索
     */
    @GetMapping
    public Response<PageVo<SearchVo>> search(@Schema(description = "关键字") @NotNull String keyword,
                                             @Schema(description = "页码") @NotNull Long pageNumber) {
        //获取商品分页列表
        PageVo<FeaturedEntity> searchData = searchService.searchCommodityPageList(keyword, pageNumber, 20L);
        List<SearchVo> searchResult = searchData.getList().stream().map(search -> {
            SearchVo searchVo = new SearchVo();
            BeanUtils.copyProperties(search, searchVo);
            searchVo.setUrl(ImageUtil.getImagePath(search.getImagePath()));
            return searchVo;
        }).toList();

        PageVo<SearchVo> pageVo = new PageVo<>();
        pageVo.setPages(searchData.getPages());
        pageVo.setTotal(searchData.getTotal());
        pageVo.setList(searchResult);
        return Response.success(BusinessStates.SUCCESS, pageVo);
    }
}
