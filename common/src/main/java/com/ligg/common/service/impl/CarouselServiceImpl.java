package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.enums.CarouselStatus;
import com.ligg.common.mapper.CarouselMapper;
import com.ligg.common.service.CarouselService;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.vo.CarouselVo;
import com.ligg.common.vo.ImagesVo;
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
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, CarouselEntity> implements CarouselService {

    @Value("${api.base-url}")
    private String BASEURL;

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
                .eq(CarouselEntity::getStatus, CarouselStatus.ENABLED.getCode())
                .orderByAsc(CarouselEntity::getSort));

        return entityList.stream().map(entity -> {
            CarouselVo carouselVo = new CarouselVo();
            BeanUtils.copyProperties(entity, carouselVo);
            ImagesVo imagePath = imageUtil.getImagePath(entity.getImagePath());
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
