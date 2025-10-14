/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
package com.ligg.entrance.component;

import com.ligg.common.constants.OrderConstant;
import com.ligg.common.constants.ProductConstant;
import com.ligg.common.mapper.order.OrderMapper;
import com.ligg.common.module.dto.ProductStockDto;
import com.ligg.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
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
    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void loadStockToRedis() {
        int offset = 0;
        int limit = 1000; // 每批处理1000条记录
        int totalLoaded = 0;

        List<ProductStockDto> batch;
        do {
            batch = orderMapper.getProductStockByBatch(offset, limit);
            for (ProductStockDto productStock : batch) {
                redisUtil.set(ProductConstant.STOCK_KEY_PREFIX + productStock.getProductId(), productStock.getStock());
            }
            totalLoaded += batch.size();
            log.info("已缓存 {{}} 条商品库存记录", totalLoaded);
            offset += limit;
        } while (!batch.isEmpty() && batch.size() == limit); // 当返回记录数小于limit时，说明已经处理完所有数据
        log.info("Redis 商品库存恢复完成,共加载：{{}}", totalLoaded);
    }

    /**
     * 定时任务：每隔 10 分钟同步 Redis -> MySQL 库存
     * 避免 Redis 异常退出或丢失数据导致库存不一致
     * <p>
     * 注意：在高并发环境中，建议使用消息队列（如 MQ 批量更新）减少数据库写压力。
     */
    @Scheduled(fixedDelay = OrderConstant.ORDER_TIMEOUT) // 10 分钟
    public void syncRedisStockToDB() {
        int offset = 0;
        int limit = 1000; // 每批处理1000条记录
        int updated = 0;

        List<ProductStockDto> batch;
        do {
            batch = orderMapper.getProductStockByBatch(offset, limit);
            for (ProductStockDto productStock : batch) {
                Integer redisStock = (Integer) redisUtil.get(ProductConstant.STOCK_KEY_PREFIX + productStock.getProductId());
                if (redisStock != null && !redisStock.equals(productStock.getStock())) {
                    try {
                        orderMapper.updateProductStock(productStock.getProductId(), redisStock);
                        updated++;
                    } catch (NumberFormatException ignored) {
                        log.error("Redis 库存数据异常：{}", redisStock);
                    }
                }
            }
            offset += limit;
        } while (!batch.isEmpty() && batch.size() == limit); // 当返回记录数小于limit时，说明已经处理完所有数据
        log.info("定时同步 Redis 库存到 MySQL 完成,共更新:{{}}条,每十分钟同步一次", updated);
    }
}
