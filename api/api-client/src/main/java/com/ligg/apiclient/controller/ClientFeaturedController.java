/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.statuEnum.BusinessStates;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.FeaturedVo;
import com.ligg.common.vo.ImagesVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 精选商品接口
 */
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
    public Response<List<FeaturedVo>> list() {
        List<FeaturedEntity> featuredList = featuredService.list();
        FeaturedVo featuredVo = new FeaturedVo();
        List<FeaturedVo> featuredVoList = featuredList.stream().map(featured -> {
            BeanUtils.copyProperties(featured, featuredVo);
            ImagesVo imagePath = imageUtil.getImagePath(featured.getImagePath());
            featuredVo.setImages(imagePath);
            return featuredVo;
        }).toList();
        return Response.success(BusinessStates.SUCCESS, featuredVoList);
    }
}

