package com.ligg.apiclient.service;

import com.ligg.common.vo.CarouselVo;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
public interface ClientCarouselService {

    /**
     * 获取轮播图列表
     * @return
     */
    List<CarouselVo> getCarouselList();
}
