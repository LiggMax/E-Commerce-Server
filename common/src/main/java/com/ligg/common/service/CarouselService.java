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


}
