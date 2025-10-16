/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
package com.ligg.apicommon.controller;

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.entity.AreaEntity;
import com.ligg.common.service.AreaService;
import com.ligg.common.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 地区接口
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/area")
public class AreaController {

    private final AreaService areaService;

    /**
     * 获取所有地区
     */
    @GetMapping
    public Response<List<AreaEntity>> getArea(){
        return Response.success(BusinessStates.SUCCESS,areaService.list());
    }
}
