/**
 * @Author Ligg
 * @Time 2025/9/25
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.service.CarouselService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.Response;
import com.ligg.common.module.vo.CarouselVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 轮播图接口
 */
@Tag(name = "客户端轮播图接口")
@RestController
@RequestMapping("/api/client/carousel")
@RequiredArgsConstructor
public class ClientCarouselController {

    private final CarouselService carouselService;

    /**
     * 获取轮播图数据列表
     */
    @Operation(summary = "获取轮播图数据列表")
    @GetMapping
    public Response<List<CarouselVo>> getCarousel() {
        return Response.success(BusinessStates.SUCCESS,
                carouselService.getCarouselList());
    }
}
