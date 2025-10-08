/**
 * @Author Ligg
 * @Time 2025/10/8
 **/
package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.mapper.SearchMapper;
import com.ligg.common.service.SearchService;
import com.ligg.common.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    /**
     * 搜索商品
     *
     * @param keyword    关键字
     * @param pageNumber 页码
     * @param pageSize   每页数量
     * @param sort       排序(1: 默认排序 2:价格 )
     * @return 商品列表
     */
    @Override
    public PageVo<FeaturedEntity> searchCommodityPageList(String keyword, Long pageNumber, long pageSize, Integer sort) {
        Page<FeaturedEntity> page = new Page<>(pageNumber, pageSize);
        IPage<FeaturedEntity> result = searchMapper.selectCommodity(page, keyword,sort);
        PageVo<FeaturedEntity> pageVo = new PageVo<>();
        pageVo.setPages(result.getPages());
        pageVo.setTotal(result.getTotal());
        pageVo.setList(result.getRecords());
        return pageVo;
    }
}
