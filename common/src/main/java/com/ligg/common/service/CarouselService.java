package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.vo.CarouselVo;
import com.ligg.common.vo.PageVo;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
public interface CarouselService extends IService<CarouselEntity> {

    /**
     * 获取轮播图列表
     * @return 轮播图列表
     */
    List<CarouselVo> getCarouselList();

    /**
     * 分页获取轮播图数据
     * @param pageNumber 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageVo<CarouselEntity> getCarouselPage(Long pageNumber, Long pageSize);

}
