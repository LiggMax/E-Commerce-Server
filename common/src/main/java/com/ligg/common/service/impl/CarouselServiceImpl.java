package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.module.entity.CarouselEntity;
import com.ligg.common.enums.UserStatus;
import com.ligg.common.mapper.CarouselMapper;
import com.ligg.common.service.CarouselService;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.module.vo.CarouselVo;
import com.ligg.common.module.vo.ImagesVo;
import com.ligg.common.module.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/23
 **/
@Service
@RequiredArgsConstructor
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, CarouselEntity> implements CarouselService {

    private final CarouselMapper carouselMapper;

    /**
     * 获取轮播图列表
     *
     * @return List<CarouselVo>
     */
    @Override
    public List<CarouselVo> getCarouselList() {
        List<CarouselEntity> entityList = carouselMapper.selectList(new LambdaQueryWrapper<CarouselEntity>()
                .eq(CarouselEntity::getStatus, UserStatus.ENABLED.getCode())
                .orderByAsc(CarouselEntity::getSort));

        return entityList.stream().map(entity -> {
            CarouselVo carouselVo = new CarouselVo();
            BeanUtils.copyProperties(entity, carouselVo);
            ImagesVo imagePath = ImageUtil.getImagePath(entity.getImagePath());
            carouselVo.setImages(imagePath);
            return carouselVo;
        }).toList();
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

        //封装PageVo
        PageVo<CarouselEntity> pageVo = new PageVo<>();
        pageVo.setPages(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setList(page.getRecords());
        return pageVo;
    }

}
