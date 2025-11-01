package com.ligg.apiadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.apiadmin.mapper.OrderManagementMapper;
import com.ligg.apiadmin.pojo.vo.OrderListVo;
import com.ligg.apiadmin.service.OrderManagementService;
import com.ligg.common.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author Ligg
 * @Time 2025/11/1
 **/
@Service
@RequiredArgsConstructor
public class OrderManagementServiceImpl implements OrderManagementService {

    private final OrderManagementMapper orderManagementMapper;

    /**
     * 订单列表
     */
    @Override
    public IPage<OrderListVo> list(Long pageNumber, Long pageSize, OrderStatus status, String keyword) {
        IPage<OrderListVo> page = new Page<>(pageNumber, pageSize);
        IPage<OrderListVo> productList = orderManagementMapper.list(page, status, keyword);

        return productList;
    }
}
