/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.common.module.entity.ProductSpecEntity;
import com.ligg.common.module.entity.ProductSpecValueEntity;
import com.ligg.common.mapper.ProductSpecMapper;
import com.ligg.common.mapper.SpecValueMapper;
import com.ligg.common.service.SpecService;
import com.ligg.common.module.vo.SpecVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecServiceImpl implements SpecService {

    private final ProductSpecMapper productSpecMapper;
    private final SpecValueMapper specValueMapper;

    /**
     * 根据id获取规格信息
     */
    @Override
    public ProductSpecEntity getSpecById(Integer id) {
        return productSpecMapper.selectById(id);
    }

    /**
     * 获取商品规格列表
     *
     * @param productId 商品id
     * @return 商品规格列表
     */
    @Override
    public List<ProductSpecEntity> getSpecListByProductId(String productId) {
        return productSpecMapper.selectList(new LambdaQueryWrapper<ProductSpecEntity>()
                .eq(ProductSpecEntity::getProductId, productId));
    }

    /**
     * 获取规格内容列表
     *
     * @param specId 规格id
     */
    @Override
    public List<ProductSpecValueEntity> getSpecValueListBySpecId(Integer specId) {
        return specValueMapper.selectList(new LambdaQueryWrapper<ProductSpecValueEntity>()
                .eq(ProductSpecValueEntity::getSpecId, specId));
    }

    /**
     * 添加商品规格
     *
     * @param spec 商品规格
     * @return 添加结果
     */
    @Override
    public int addSpec(ProductSpecEntity spec) {
        return productSpecMapper.insert(spec);
    }

    /**
     * 添加商品规格内容
     *
     * @param specValueEntity 商品规格内容
     */
    @Override
    public int addSpecValue(ProductSpecValueEntity specValueEntity) {
        return specValueMapper.insert(specValueEntity);
    }

    /**
     * 获取商品规格详情
     *
     * @param productId 商品id
     * @return 商品规格详情
     */
    @Override
    public List<SpecVo> getSpecDetailByProductId(String productId) {

        List<ProductSpecEntity> specList = getSpecListByProductId(productId);
        return specList.stream().map(specEntity -> {
            SpecVo specVo = new SpecVo();
            BeanUtils.copyProperties(specEntity, specVo);

            List<ProductSpecValueEntity> valueListBySpec = getSpecValueListBySpecId(specVo.getId());
            specVo.setSpecValues(valueListBySpec.stream().map(valueEntity -> {
                SpecVo.SpecValueVo specValueVo = new SpecVo.SpecValueVo();
                BeanUtils.copyProperties(valueEntity, specValueVo);
                return specValueVo;
            }).toList());
            return specVo;
        }).toList();
    }
}
