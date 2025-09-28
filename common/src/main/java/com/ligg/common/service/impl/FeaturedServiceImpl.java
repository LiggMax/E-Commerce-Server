package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.mapper.FeaturedMapper;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
@Service
public class FeaturedServiceImpl extends ServiceImpl<FeaturedMapper, FeaturedEntity> implements FeaturedService {

    @Value("${api.base-url}")
    private String BASEURL;

    @Autowired
    private FeaturedMapper featuredMapper;

    @Override
    public void saveFeatured(FeaturedEntity featuredEntity) {
        featuredMapper.insert(featuredEntity);
    }

    @Override
    public PageVo<FeaturedEntity> Pagelist(Long pageNumber, Long pageSize) {
        //创建分页对象
        Page<FeaturedEntity> page = new Page<>(pageNumber, pageSize);

        // 分页查询
        featuredMapper.selectPage(page, new LambdaQueryWrapper<FeaturedEntity>()
                .orderByAsc(FeaturedEntity::getRating));

        //封装PageVo
        PageVo<FeaturedEntity> pageVo = new PageVo<>();
        pageVo.setPages(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setList(page.getRecords());
        return pageVo;
    }

}
