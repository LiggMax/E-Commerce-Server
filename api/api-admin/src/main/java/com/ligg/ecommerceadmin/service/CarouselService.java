package com.ligg.ecommerceadmin.service;

import com.ligg.entity.admin.CarouselEntity;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
public interface CarouselService {

    /**
     * 上传轮播图数据
     */
    int upload(CarouselEntity carousel);

    /**
     * 获取轮播图数据
     */
    List<CarouselEntity> getCarousel();
}
