/**
 * @author Ligg
 * @Time 2025/10/25
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商品收藏接口", description = "管理商品收藏")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/client/user/favorite")
public class ClientProductFavoriteController {

    private final UserService userService;

    /**
     * 商品收藏
     */
    @PostMapping
    public Response<String> productFavorite(@NotNull Long productId, @NotNull boolean isFavorite) {
        return userService.productFavorite(productId, isFavorite) < 1
                ? Response.error(BusinessStates.INTERNAL_SERVER_ERROR)
                : Response.success(BusinessStates.SUCCESS);
    }
}
