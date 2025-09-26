/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.apiadmin.service;

import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.vo.PageVo;

public interface AdminCarouselService {

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
