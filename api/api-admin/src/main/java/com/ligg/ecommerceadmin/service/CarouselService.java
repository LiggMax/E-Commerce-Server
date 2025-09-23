package com.ligg.ecommerceadmin.service;

import com.ligg.entity.admin.CarouselEntity;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
public interface CarouselService {

    /**
     * 上传轮播图数据
     */
    int upload(CarouselEntity carousel);
}
