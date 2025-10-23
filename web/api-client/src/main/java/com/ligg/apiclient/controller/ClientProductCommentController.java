package com.ligg.apiclient.controller;

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.dto.ProductCommentDto;
import com.ligg.common.service.product.ProductCommentService;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Tag(name = "商品评价接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/product/comment")
public class ClientProductCommentController {

    private final ProductCommentService commentService;
    /**
     * 发布商品评价
     */
    @PostMapping
    public Response<String> publishComment(ProductCommentDto content) {
        commentService.publishComment(content);
        return Response.success(BusinessStates.SUCCESS);
    }
}
