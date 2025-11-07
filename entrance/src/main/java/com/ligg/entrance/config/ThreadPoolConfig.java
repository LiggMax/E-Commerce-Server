package com.ligg.entrance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
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

    @Bean("emailTaskExecutor")
    public ThreadPoolTaskExecutor mailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(5);
        // 最大线程数
        executor.setMaxPoolSize(10);
        // 队列容量
        executor.setQueueCapacity(100);
        // 线程前缀名
        executor.setThreadNamePrefix("email-async-");
        // 线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
        executor.setAwaitTerminationSeconds(60);
        // 拒绝策略：由调用线程处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        executor.initialize();
        return executor;
    }

}
