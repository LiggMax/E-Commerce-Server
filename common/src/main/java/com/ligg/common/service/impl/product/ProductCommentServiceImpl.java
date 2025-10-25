package com.ligg.common.service.impl.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.AuditStatu;
import com.ligg.common.mapper.product.ProductCommentMapper;
import com.ligg.common.module.bo.ProductCommentBo;
import com.ligg.common.module.entity.ProductCommentEntity;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.module.vo.ProductCommentVo;
import com.ligg.common.service.product.ProductCommentService;
import com.ligg.common.utils.ThreadLocalUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Service
@RequiredArgsConstructor
public class ProductCommentServiceImpl implements ProductCommentService {

    private final ProductCommentMapper commentMapper;
    @Override
    public int publishComment(ProductCommentBo content) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        String userIp = (String) userInfo.get(UserConstant.USER_IP);

        ProductCommentEntity commentEntity = new ProductCommentEntity();
        BeanUtils.copyProperties(content, commentEntity);
        commentEntity.setUserId(userId);
        //暂时先不审核
        commentEntity.setStatus(AuditStatu.APPROVED);
        if (content.getIsAnonymous() != null){
            commentEntity.setIsAnonymous(content.getIsAnonymous());
        }
        commentEntity.setIpAddress(userIp);
        commentEntity.setCreateTime(LocalDateTime.now());
        commentEntity.setUpdateTime(LocalDateTime.now());

         return commentMapper.insert(commentEntity);
    }

    /**
     * 获取商品评价
     */
    @Override
    public PageVo<ProductCommentVo> getCommentByProductId(@NotNull Long productId, Long pageNumber, Long pageSize) {
        IPage<ProductCommentVo> Page = new Page<>(pageNumber, pageSize);
        IPage<ProductCommentVo> result = commentMapper.selectCommentByProductId(Page,productId);

        PageVo<ProductCommentVo> pageVo = new PageVo<>();
        pageVo.setTotal(result.getTotal());
        pageVo.setPages(result.getPages());
        pageVo.setList(result.getRecords());
        return pageVo;
    }
}
