package com.ligg.common.service;

import com.ligg.common.entity.SpecEntity;
import com.ligg.common.entity.SpecValueEntity;
import com.ligg.common.vo.SpecVo;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
public interface SpecService {

    /**
     * 根据id获取规格信息
     */
    SpecEntity getSpecById(Integer id);

    /**
     * 获取商品规格列表
     *
     * @param productId 商品id
     * @return 商品规格
     */
    List<SpecEntity> getSpecListByProductId(String productId);

    /**
     * 获取规格内容列表
     *
     * @param specId 规格id
     */
    List<SpecValueEntity> getSpecValueListBySpecId(Integer specId);

    /**
     * 添加商品规格
     *
     * @param spec 商品规格
     */
    int addSpec(SpecEntity spec);

    /**
     * 添加商品规格内容
     *
     * @param specValueEntity 商品规格内容
     */
    int addSpecValue(SpecValueEntity specValueEntity);

    /**
     * 获取商品规格详情
     *
     * @param productId 商品id
     * @return 商品规格详情
     */
    List<SpecVo> getSpecDetailByProductId(String productId);
}
