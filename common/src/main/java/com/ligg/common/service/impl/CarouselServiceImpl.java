package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.mapper.CarouselMapper;
import com.ligg.common.service.CarouselService;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.vo.CarouselVo;
import com.ligg.common.vo.PageVo;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private CarouselMapper carouselMapper;

    @Autowired
    private ImageUtil imageUtil;



    /**
     * 获取轮播图列表
     *
     * @return List<CarouselVo>
     */
    @Override
    public List<CarouselVo> getCarouselList() {
        List<CarouselEntity> entityList = carouselMapper.selectList(new LambdaQueryWrapper<CarouselEntity>()
                .orderByAsc(CarouselEntity::getSort));

        return entityList.stream().map(entity -> {
            CarouselVo carouselVo = new CarouselVo();
            BeanUtils.copyProperties(entity, carouselVo);
            //TODO 处理图片路径
            CarouselVo.Images imagePath = imageUtil.getImagePath(entity.getImagePath());
            carouselVo.setImagePath(imagePath);
            return carouselVo;
        }).toList();
    }


}
