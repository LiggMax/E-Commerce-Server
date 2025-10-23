package com.ligg.apiclient.controller;

import com.ligg.common.constants.Constant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.bo.ProductCommentBo;
import com.ligg.common.module.dto.ProductCommentDto;
import com.ligg.common.service.FileService;
import com.ligg.common.service.product.ProductCommentService;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Tag(name = "商品评价接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/user/product/comment")
public class ClientProductCommentController {

    private final ProductCommentService commentService;

    private final FileService fileService;

    /**
     * 发布商品评价
     */
    @PostMapping
    public Response<String> publishComment(@RequestParam("images") MultipartFile[] imageFiles,
                                           ProductCommentDto content) {

        if (imageFiles.length > 5 ) {
            return Response.error(BusinessStates.VALIDATION_FAILED);
        }

        List<String> imagePaths = new ArrayList<>();
        // 遍历上传图片
        for (MultipartFile imageFile : imageFiles) {
            if (imageFile != null && !imageFile.isEmpty() && imageFile.getSize() < Constant.FILE_SIZE){
                String imagePath = fileService.uploadImage(imageFile, Constant.COMMENT_FILE_PATH);
                imagePaths.add(imagePath);
            }
        }
        ProductCommentBo commentBo = new ProductCommentBo();
        BeanUtils.copyProperties(content, commentBo);
        commentBo.setImages(imagePaths);
        commentService.publishComment(commentBo);
        return Response.success(BusinessStates.SUCCESS);
    }
}
