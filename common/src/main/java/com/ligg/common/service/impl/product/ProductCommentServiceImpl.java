package com.ligg.common.service.impl.product;

import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.AuditStatu;
import com.ligg.common.mapper.product.ProductCommentMapper;
import com.ligg.common.module.bo.ProductCommentBo;
import com.ligg.common.module.entity.ProductCommentEntity;
import com.ligg.common.service.product.ProductCommentService;
import com.ligg.common.utils.ThreadLocalUtil;
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

        ProductCommentEntity commentEntity = new ProductCommentEntity();
        BeanUtils.copyProperties(content, commentEntity);
        commentEntity.setUserId(userId);
        //暂时先不审核
        commentEntity.setStatus(AuditStatu.APPROVED);
        if (content.getIsAnonymous() != null){
            commentEntity.setIsAnonymous(content.getIsAnonymous());
        }
        //TODO 后续需要将ip存在Token中
        commentEntity.setIpAddress("127.0.0.1");
        commentEntity.setCreateTime(LocalDateTime.now());
        commentEntity.setUpdateTime(LocalDateTime.now());

         return commentMapper.insert(commentEntity);
    }
}
