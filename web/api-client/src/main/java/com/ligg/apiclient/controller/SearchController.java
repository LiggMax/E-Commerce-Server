/**
 * @Author Ligg
 * @Time 2025/10/8
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.SearchService;
import com.ligg.common.utils.DiscountUtil;
import com.ligg.common.utils.ImageUtil;
import com.ligg.common.utils.Response;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.module.vo.search.SearchVo;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 搜索接口
 */
@Tag(name = "客户端搜索接口")
@RestController
@RequestMapping("/api/client/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;


}
