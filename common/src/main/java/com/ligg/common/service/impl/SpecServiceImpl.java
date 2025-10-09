/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.common.entity.ProductEntity;
import com.ligg.common.entity.SpecEntity;
import com.ligg.common.entity.SpecValueEntity;
import com.ligg.common.mapper.SpecMapper;
import com.ligg.common.mapper.SpecValueMapper;
import com.ligg.common.service.SpecService;
import com.ligg.common.vo.SpecVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    /**
     * 根据id获取规格信息
     */
    @Override
    public SpecEntity getSpecById(Integer id) {
        return specMapper.selectById(id);
    }

    /**
     * 获取商品规格列表
     *
     * @param productId 商品id
     * @return 商品规格列表
     */
    @Override
    public List<SpecEntity> getSpecListByProductId(String productId) {
        return specMapper.selectList(new LambdaQueryWrapper<SpecEntity>()
                .eq(SpecEntity::getProductId, productId));
    }

    /**
     * 获取规格内容列表
     *
     * @param specId 规格id
     */
    @Override
    public List<SpecValueEntity> getSpecValueListBySpecId(Integer specId) {
        return specValueMapper.selectList(new LambdaQueryWrapper<SpecValueEntity>()
                .eq(SpecValueEntity::getSpecId, specId));
    }

    /**
     * 添加商品规格
     *
     * @param spec 商品规格
     * @return 添加结果
     */
    @Override
    public int addSpec(SpecEntity spec) {
        return specMapper.insert(spec);
    }

    /**
     * 添加商品规格内容
     *
     * @param specValueEntity 商品规格内容
     */
    @Override
    public int addSpecValue(SpecValueEntity specValueEntity) {
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

        List<SpecEntity> specList = getSpecListByProductId(productId);
        return specList.stream().map(specEntity -> {
            SpecVo specVo = new SpecVo();
            BeanUtils.copyProperties(specEntity, specVo);

            List<SpecValueEntity> valueListBySpec = getSpecValueListBySpecId(specVo.getId());
            specVo.setSpecValues(valueListBySpec.stream().map(valueEntity -> {
                SpecVo.SpecValueVo specValueVo = new SpecVo.SpecValueVo();
                BeanUtils.copyProperties(valueEntity, specValueVo);
                return specValueVo;
            }).toList());
            return specVo;
        }).toList();
    }
}
