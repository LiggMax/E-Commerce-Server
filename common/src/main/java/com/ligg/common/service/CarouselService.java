package com.ligg.common.service;

import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.vo.CarouselVo;
import com.ligg.common.vo.PageVo;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
public interface CarouselService {

    /**
     * 获取轮播图列表
     * @return
     */
    List<CarouselVo> getCarouselList();

    /**
     * 上传轮播图数据
     */
    int save(CarouselEntity carousel);

    /**
     * 分页获取轮播图数据
     * @param pageNumber 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageVo<CarouselEntity> getCarouselPage(Long pageNumber, Long pageSize);

}
