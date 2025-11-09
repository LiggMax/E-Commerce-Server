package com.ligg.apiclient.controller;

import com.ligg.common.constants.Constant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.bo.ProductCommentBo;
import com.ligg.common.module.dto.PaymentDto;
import com.ligg.common.module.dto.ProductCommentDto;
import com.ligg.common.module.dto.UserDto;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.service.FileService;
import com.ligg.common.service.UserService;
import com.ligg.common.service.product.ProductCommentService;
import com.ligg.common.utils.Response;
import com.ligg.common.utils.ThreadLocalUtil;
import com.ligg.common.module.vo.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Ligg
 * @time 2025/10/10
 * @update_time 2025/11/6 14:28
 **/
@Slf4j
@Tag(name = "用户接口", description = "管理账户信息")
@RestController
@RequestMapping("/api/client/user")
@RequiredArgsConstructor
public class ClientUserController {

    private final UserService userService;

    private final ProductCommentService commentService;

    private final FileService fileService;

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public Response<UserInfoVo> getUserInfo() {
        UserInfoVo userInfo = userService.getUserInfo();
        return Response.success(BusinessStates.SUCCESS, userInfo);
    }

    @PutMapping
    public Response<String> updateUserInfo(@Validated UserDto userDto,
                                           MultipartFile avatarFile) {
        if (avatarFile != null && !avatarFile.isEmpty() && Objects.requireNonNull(avatarFile.getContentType()).startsWith("image")) {
            if (avatarFile.getSize() > 1024 * 1024 * 2) {
                return Response.error(BusinessStates.VALIDATION_FAILED, "图片大小不能超过2M");
            }
            userService.updateUserAvatar(avatarFile);
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        return userService.updateUserInfo(userEntity) < 1
                ? Response.error(BusinessStates.INTERNAL_SERVER_ERROR)
                : Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 发布商品评价
     */
    @PostMapping("/comment")
    public Response<String> publishComment(@RequestParam(required = false) MultipartFile[] imageFiles,
                                           ProductCommentDto content) {

        ProductCommentBo commentBo = new ProductCommentBo();
        BeanUtils.copyProperties(content, commentBo);
        if (imageFiles != null && imageFiles.length < 6) {
            List<String> imagePaths = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                if (imageFile != null && !imageFile.isEmpty()) {
                    if (imageFile.getSize() > Constant.FILE_SIZE) {
                        return Response.error(BusinessStates.FILE_UPLOAD_FAILED, "图片大小不能超过2M");
                    }
                    String imagePath = fileService.uploadImage(imageFile, Constant.COMMENT_FILE_PATH);
                    imagePaths.add(imagePath);
                }
            }
            commentBo.setImages(imagePaths);
        }

        return commentService.publishComment(commentBo) < 1
                ? Response.error(BusinessStates.INTERNAL_SERVER_ERROR)
                : Response.success(BusinessStates.SUCCESS);
    }


    /**
     * 余额充值
     */
    @PatchMapping("/recharge")
    public Response<String> recharge(@RequestBody PaymentDto payment) {
        //金额是否小于0
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return Response.error(BusinessStates.VALIDATION_FAILED);
        }
        //TODO 后续在对充值类型进行处理
        log.info("{}:充值", payment.getPayType());
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        return userService.recharge(payment.getAmount(), userId) < 1
                ? Response.error(BusinessStates.INTERNAL_SERVER_ERROR)
                : Response.success(BusinessStates.SUCCESS);
    }

    @Operation(summary = "商品收藏")
    @PostMapping("/favorite")
    public Response<String> productFavorite(@NotNull Long productId, @NotNull boolean isFavorite) {
        return userService.productFavorite(productId, isFavorite) < 1
                ? Response.error(BusinessStates.INTERNAL_SERVER_ERROR)
                : Response.success(BusinessStates.SUCCESS);
    }
}
