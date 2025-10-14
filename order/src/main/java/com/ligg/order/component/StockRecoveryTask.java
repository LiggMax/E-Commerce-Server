/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
package com.ligg.order.component;

import com.ligg.common.constants.ProductConstant;
import com.ligg.common.module.dto.ProductStockDto;
import com.ligg.common.utils.RedisUtil;
import com.ligg.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis 重启库存恢复 + 定时同步库存任务
 * 1. 应用启动时（或 Redis 重启后）从数据库加载库存信息到 Redis
 * 2. 定时任务每隔 10 分钟将 Redis 中的库存与数据库库存同步，确保一致性
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class StockRecoveryTask {

    private final RedisUtil redisUtil;
    private final OrderMapper orderMapper;


    /**
     * 应用启动后恢复 Redis 库存
     * 通常用于 Redis 重启或数据丢失的情况
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadStockToRedis() {
        List<ProductStockDto> allProductStock = orderMapper.getAllProductStock();
        for (ProductStockDto productStock : allProductStock) {
            redisUtil.set(ProductConstant.STOCK_KEY_PREFIX + productStock.getProductId(), productStock.getStock());
        }
        log.info("Redis 库存恢复完成,共加载：{}", allProductStock.size());
    }

    /**
     * 定时任务：每隔 10 分钟同步 Redis -> MySQL 库存
     * 避免 Redis 异常退出或丢失数据导致库存不一致
     * <p>
     * 注意：在高并发环境中，建议使用消息队列（如 MQ 批量更新）减少数据库写压力。
     */
    @Scheduled(fixedDelay = 10 * 60 * 1000) // 10 分钟
    public void syncRedisStockToDB() {
        List<ProductStockDto> allProductStock = orderMapper.getAllProductStock();
        int updated = 0;
        for (ProductStockDto productStock : allProductStock) {
            Integer redisStock = (Integer) redisUtil.get(ProductConstant.STOCK_KEY_PREFIX + productStock.getProductId());
            if (redisStock != null && !redisStock.equals(productStock.getStock())) {
                try {
                    orderMapper.updateProductStock(productStock.getProductId(), redisStock);
                    updated++;
                } catch (NumberFormatException ignored) {
                    log.error("Redis 库存数据异常：{}", redisStock);
                }
            }
            log.info("同步 Redis 库存到 MySQL 完成,共更新：{}", updated);
        }
    }
}
