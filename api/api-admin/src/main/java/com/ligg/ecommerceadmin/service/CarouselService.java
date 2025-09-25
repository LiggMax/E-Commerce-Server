package com.ligg.ecommerceadmin.service;

import com.ligg.entity.CarouselEntity;
import com.ligg.vo.PageVo;

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
     * 分页获取轮播图数据
     * @param pageNumber 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageVo<CarouselEntity> getCarouselPage(Long pageNumber, Long pageSize);
}
