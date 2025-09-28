//package com.ligg.entrance.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
//
///**
// * @Author Ligg
// * @Time 2025/9/28
// **/
//@Configuration
//public class ThreadPoolConfig {
//
//    @Bean("myExecutor")
//    public ThreadPoolTaskExecutor myExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);
//        executor.setMaxPoolSize(20);
//        executor.setQueueCapacity(50);
//        executor.setKeepAliveSeconds(60);
//        executor.setThreadNamePrefix("MyExecutor-");
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.setAwaitTerminationSeconds(30);
//        executor.initialize();
//        return executor;
//    }
//
//    @Bean("fastExecutor")
//    public ThreadPoolTaskExecutor fastExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(5);
//        executor.setQueueCapacity(10);
//        executor.setThreadNamePrefix("FastExecutor-");
//        executor.initialize();
//        return executor;
//    }
//}
