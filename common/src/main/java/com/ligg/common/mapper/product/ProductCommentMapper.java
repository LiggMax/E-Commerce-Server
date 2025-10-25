package com.ligg.common.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.common.module.entity.ProductCommentEntity;
import com.ligg.common.module.vo.ProductCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Mapper
public interface ProductCommentMapper extends BaseMapper<ProductCommentEntity> {
    IPage<ProductCommentVo> selectCommentByProductId(@Param("page") IPage<ProductCommentVo> page, @Param("productId") Long productId);
}
