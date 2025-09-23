package com.ligg.ecommerceadmin.service.impl;

import com.ligg.ecommerceadmin.mapper.CarouselMapper;
import com.ligg.ecommerceadmin.service.CarouselService;
import com.ligg.entity.admin.CarouselEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 上传轮播图数据
     */
    @Override
    public int upload(CarouselEntity carousel) {
        return carouselMapper.insert(carousel);
    }
}
