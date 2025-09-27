package com.ligg.apiclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.apiclient.mapper.ClientCarouselMapper;
import com.ligg.apiclient.service.ClientCarouselService;
import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.vo.CarouselVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
@Service
public class ClientCarouselServiceImpl implements ClientCarouselService {

    @Autowired
    private ClientCarouselMapper carouselMapper;

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
            carouselVo.setImages(imagePath);
            return carouselVo;
        }).toList();
    }
}
