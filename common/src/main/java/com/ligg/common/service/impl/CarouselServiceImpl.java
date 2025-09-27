package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.common.entity.CarouselEntity;
import com.ligg.common.mapper.CarouselMapper;
import com.ligg.common.service.CarouselService;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.vo.CarouselVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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






}
