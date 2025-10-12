/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
package com.ligg.apiadmin.controller;

import com.ligg.common.module.dto.SpecDto;
import com.ligg.common.module.entity.SpecEntity;
import com.ligg.common.module.entity.SpecValueEntity;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.ProductService;
import com.ligg.common.service.SpecService;
import com.ligg.common.utils.Response;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AdminSpecController {

    private final SpecService specService;
    private final ProductService productService;

    /**
     * 新增规格
     */
    @PostMapping
    public Response<String> addSpec(@Validated @RequestBody SpecDto spec) {
        if (productService.getById(spec.getProductId()) == null) {
            return Response.error(BusinessStates.DATA_NOT_FOUND, "商品不存在");
        }
        List<SpecEntity> listByProduct = specService.getSpecListByProductId(spec.getProductId());
        if (listByProduct.size() >= 6) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "商品规格不能超过6个");
        }

        List<SpecDto.Specs> specs = spec.getSpecs();
        // 保存规格
        for (SpecDto.Specs specItem : specs) {
            SpecEntity specEntity = new SpecEntity();
            specEntity.setProductId(spec.getProductId());
            specEntity.setProductId(spec.getProductId());
            specEntity.setName(specItem.getName());
            specEntity.setSort(specItem.getSort());
            specEntity.setCreateTime(LocalDateTime.now());
            specEntity.setUpdateTime(LocalDateTime.now());
            specService.addSpec(specEntity);

            //获取保存的规格id
            Integer specId = specEntity.getId();

            //保存规格内容
            List<SpecDto.SpecValue> specValues = specItem.getSpecValues();
            for (SpecDto.SpecValue specValue : specValues) {
                SpecValueEntity specValueEntity = new SpecValueEntity();
                specValueEntity.setSpecId(specId);
                specValueEntity.setValue(specValue.getValue());
                specValueEntity.setSort(specValue.getSort());
                specValueEntity.setPrice(specValue.getPrice());
                specValueEntity.setCreateTime(LocalDateTime.now());
                specValueEntity.setUpdateTime(LocalDateTime.now());
                specService.addSpecValue(specValueEntity);
            }
        }

        return Response.success(BusinessStates.SUCCESS);
    }
}
