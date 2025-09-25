package com.ligg.apiclient.controller;

import com.ligg.common.service.CarouselService;
import com.ligg.common.statuEnum.BusinessStates;
import com.ligg.common.utils.Response;
import com.ligg.common.vo.CarouselVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/25
 **/

/**
 * 轮播图接口
 */
@RestController
@RequestMapping("/api/client/carousel")
public class ClientCarouselController {

    @Autowired
    private CarouselService carouselService;

    /**
     * 获取轮播图数据列表
     */
    @GetMapping
    public Response<List<CarouselVo> > getCarousel() {
        List<CarouselVo> carouselList = carouselService.getCarouselList();
        return Response.success(BusinessStates.SUCCESS,carouselList);
    }
}
