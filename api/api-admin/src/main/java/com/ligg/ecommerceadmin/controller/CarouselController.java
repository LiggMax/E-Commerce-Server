package com.ligg.ecommerceadmin.controller;

import com.ligg.ecommerceadmin.service.CarouselService;
import com.ligg.entity.admin.CarouselEntity;
import com.ligg.statuEnum.BusinessStates;
import com.ligg.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Ligg
 * @Time 2025/9/23
 *
 * 轮播图控制器
 **/
@RestController
@RequestMapping("/api/admin/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;
    /**
     * 上传轮播图数据
     */
    @PostMapping("/upload")
    public Response<String> upload(@RequestBody CarouselEntity carousel) {

        int res = carouselService.upload(carousel);
        return Response.success(BusinessStates.SUCCESS);
    }
}
