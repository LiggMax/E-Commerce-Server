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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        List<FeaturedVo> featuredVoList = featuredList.stream().map(featured -> {
            FeaturedVo featuredVo = new FeaturedVo();
            BeanUtils.copyProperties(featured, featuredVo);
            ImagesVo imagePath = imageUtil.getImagePath(featured.getImagePath());
            featuredVo.setImages(imagePath);
            Double originalPrice = featured.getOriginalPrice();
            Double currentPrice = featured.getCurrentPrice();
            // 使用BigDecimal进行精确计算
            BigDecimal original = BigDecimal.valueOf(originalPrice);
            BigDecimal current = BigDecimal.valueOf(currentPrice);
            // 计算折扣百分比
            BigDecimal discount = original.subtract(current)
                    .divide(original, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            featuredVo.setDiscount(discount.doubleValue());
            return featuredVo;
        }).toList();
        return Response.success(BusinessStates.SUCCESS, featuredVoList);
    }
}

