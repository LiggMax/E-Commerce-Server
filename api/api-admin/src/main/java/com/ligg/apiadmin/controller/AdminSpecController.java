/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.dto.SpecDto;
import com.ligg.common.dto.SpecValueDto;
import com.ligg.common.entity.SpecEntity;
import com.ligg.common.entity.SpecValueEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.ProductService;
import com.ligg.common.service.SpecService;
import com.ligg.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品规格
 */
@RestController
@RequestMapping("/api/admin/spec")
public class AdminSpecController {

    @Autowired
    private SpecService specService;

    @Autowired
    private ProductService productService;

    /**
     * 新曾规格
     */
    @PostMapping
    public Response<String> addSpec(@Validated @RequestBody SpecDto spec) {
        if(productService.getById(spec.getProductId()) == null) {
            return Response.error(BusinessStates.DATA_NOT_FOUND, "商品不存在");
        }
        List<SpecEntity> listByProduct = specService.getSpecListByProductId(spec.getProductId());
        if (listByProduct.size() >= 6) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "商品规格不能超过6个");
        }
        SpecEntity specEntity = new SpecEntity();
        specEntity.setProductId(spec.getProductId());
        specEntity.setName(spec.getName());
        specEntity.setSort(spec.getSort());
        specEntity.setCreateTime(LocalDateTime.now());
        specEntity.setUpdateTime(LocalDateTime.now());
        return specService.addSpec(specEntity) > 0
                ? Response.success(BusinessStates.SUCCESS)
                : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 新增规格内容
     */
    @PostMapping("/content")
    public Response<String> addSpecContent(@Validated @RequestBody SpecValueDto specValue) {
        if(specService.getSpecById(specValue.getSpecId()) == null) {
            return Response.error(BusinessStates.DATA_NOT_FOUND, "规格不存在");
        }
        List<SpecValueEntity> valueListBySpec = specService.getSpecValueListBySpecId(specValue.getSpecId());
        if (valueListBySpec.size() >= 20) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "商品规格内容不能超过20个");
        }
        SpecValueEntity specValueEntity = new SpecValueEntity();
        specValueEntity.setSpecId(specValue.getSpecId());
        specValueEntity.setValue(specValue.getValue());
        specValueEntity.setSort(specValue.getSort());
        specValueEntity.setCreateTime(LocalDateTime.now());
        specValueEntity.setUpdateTime(LocalDateTime.now());
        return specService.addSpecValue(specValueEntity) > 0
                ? Response.success(BusinessStates.SUCCESS)
                : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }
}
