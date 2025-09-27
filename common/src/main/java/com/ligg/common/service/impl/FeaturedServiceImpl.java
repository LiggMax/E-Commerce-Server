package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.mapper.FeaturedMapper;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.vo.FeaturedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
@Service
public class FeaturedServiceImpl extends ServiceImpl<FeaturedMapper, FeaturedEntity> implements FeaturedService {

    @Autowired
    private FeaturedMapper featuredMapper;

    @Override
    public void saveFeatured(FeaturedEntity featuredEntity) {
        featuredMapper.insert(featuredEntity);
    }

}
