package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.entity.FeaturedDetailEntity;
import com.ligg.common.mapper.FeaturedMapper;
import com.ligg.common.service.FeaturedService;
import com.ligg.common.vo.PageVo;
import com.ligg.common.vo.search.FeaturedSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 获取精选商品分页列表
     *
     * @param pageNumber 页码
     * @param pageSize   每页数量
     * @return 精选商品分页列表
     */
    @Override
    public PageVo<FeaturedEntity> getFeaturedPageList(Long pageNumber, Long pageSize) {
        Page<FeaturedEntity> page = new Page<>(pageNumber, pageSize);
        featuredMapper.selectPage(page, null);

        PageVo<FeaturedEntity> pageVo = new PageVo<>();
        pageVo.setPages(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setList(page.getRecords());
        return pageVo;
    }

    /**
     * 获取精选商品详情分页列表
     * 自定义搜索条件
     * @param pageNumber 页码
     * @param pageSize   每页数量
     * @return 精选商品详情分页列表
     */
    @Override
    public PageVo<FeaturedSearchVo> getProductDetailPagelist(Long pageNumber, Long pageSize) {
        //创建分页对象
        Page<FeaturedSearchVo> page = new Page<>(pageNumber, pageSize);

        // 分页查询
        IPage<FeaturedSearchVo> result = featuredMapper.selectProductDetailPage(page);
        //封装PageVo
        PageVo<FeaturedSearchVo> pageVo = new PageVo<>();
        pageVo.setPages(result.getPages());
        pageVo.setTotal(result.getTotal());
        pageVo.setList(result.getRecords());
        return pageVo;
    }

    /**
     * 根据商品id查询商品详情
     */
    @Override
    public FeaturedDetailEntity getFeaturedDetailById(String productId) {
        return featuredMapper.selectProductDetailById(productId);
    }

    /**
     * 根据id更新商品图片路径
     */
    @Override
    public void updateImagePathById(String id, String imagePath) {

    }


    /**
     * 根据商品id查询商品图片列表
     */
//    @Override
//    public List<FeaturedDetailVo.Images> selectProductImagesById(Long productId) {
//        return featuredMapper.selectProductImagesById(productId);
//    }
}
