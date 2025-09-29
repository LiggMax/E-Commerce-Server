package com.ligg.entrance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @Author Ligg
 * @Time 2025/9/28
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean("fileTaskExecutor")
    public ThreadPoolTaskExecutor fileTaskExecutor() {
        // 创建线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 获取CPU核数
        int cpuCores = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(cpuCores * 2);         // 设置核心线程数
        executor.setMaxPoolSize(cpuCores * 4);          // 设置最大线程数
        executor.setQueueCapacity(100);                 // 设置队列容量
        executor.setKeepAliveSeconds(60);               // 设置线程空闲时间
        executor.setThreadNamePrefix("file-io-task-");  // 设置线程名称前缀
        executor.setAwaitTerminationSeconds(120);       // 设置等待终止时间

        // ----------- 拒绝策略 -----------
        // 当线程池和队列都满时，任务由提交任务的线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化
        executor.initialize();
        return executor;
    }

}
