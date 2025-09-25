package com.ligg.ecommerceadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.ecommerceadmin.mapper.CarouselMapper;
import com.ligg.ecommerceadmin.service.CarouselService;
import com.ligg.entity.CarouselEntity;
import com.ligg.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
     * 分页获取轮播图数据
     */
    @Override
    public PageVo<CarouselEntity> getCarouselPage(Long pageNumber, Long pageSize) {
        //创建分页对象
        Page<CarouselEntity> page = new Page<>(pageNumber, pageSize);

        // 分页查询
        carouselMapper.selectPage(page, new LambdaQueryWrapper<CarouselEntity>()
                .orderByAsc(CarouselEntity::getSort));

        //处理图片路径
        page.getRecords().forEach(carousel -> {
            carousel.setImagePath(BASEURL + carousel.getImagePath());
        });

        //封装PageVo
        PageVo<CarouselEntity> pageVo = new PageVo<>();
        pageVo.setPages(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setList(page.getRecords());
        return pageVo;
    }
}
