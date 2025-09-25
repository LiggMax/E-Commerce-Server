package com.ligg.ecommerceadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.ecommerceadmin.mapper.CarouselMapper;
import com.ligg.ecommerceadmin.service.CarouselService;
import com.ligg.entity.CarouselEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
@Service
public class CarouselServiceImpl implements CarouselService {

    @Value("${api.base-url}")
    private String BASEURL;

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 上传轮播图数据
     */
    @Override
    public int upload(CarouselEntity carousel) {
        return carouselMapper.insert(carousel);
    }

    /**
     * 获取轮播图数据
     *
     * @return 轮播图数据集合
     */
    @Override
    public List<CarouselEntity> getCarousel() {
        List<CarouselEntity> entityList = carouselMapper.selectList(new LambdaQueryWrapper<CarouselEntity>()
                .orderByAsc(CarouselEntity::getSort));
        for (CarouselEntity entity : entityList) {
            entity.setImagePath(BASEURL + entity.getImagePath());
        }
        return entityList;
    }
}
