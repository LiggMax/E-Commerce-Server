package com.ligg.entrance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Ligg
 * @Time 2025/9/28
 **/
@SpringBootTest
public class ThreadPoolTest {

    @Qualifier("applicationTaskExecutor")
    @Autowired
    private TaskExecutor applicationTaskExecutor;

    /**
     * 测试线程池配置
     */
    @Test
    public void testThreadPoolConfiguration() {
        // 验证线程池是否正确配置并注入
        assertNotNull(applicationTaskExecutor);
        assertInstanceOf(ThreadPoolTaskExecutor.class, applicationTaskExecutor);

        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) applicationTaskExecutor;
        assertEquals("task-", executor.getThreadNamePrefix());
        assertEquals(8, executor.getCorePoolSize());
        assertEquals(20, executor.getMaxPoolSize());
        assertEquals(50, executor.getQueueCapacity());
    }

    /**
     * 测试线程池执行任务
     */
    @Test
    public void testThreadPoolExecution() throws InterruptedException {
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) applicationTaskExecutor;

        // 创建计数器，验证任务执行
        int taskCount = 5;
        CountDownLatch latch = new CountDownLatch(taskCount);

        // 提交多个任务到线程池
        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                try {
                    // 模拟任务处理时间
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                latch.countDown();
            });
        }

        // 等待所有任务完成
        boolean finished = latch.await(5, TimeUnit.SECONDS);
        assertTrue(finished, "All tasks should complete within 5 seconds");
    }

    /**
     * 测试使用CompletableFuture的异步方法
     */
    @Test
    public void testAsyncMethodWithCompletableFuture() {
        // 测试使用CompletableFuture的异步方法
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Task completed in " + Thread.currentThread().getName();
        }, applicationTaskExecutor);

        String result = future.join();
        assertNotNull(result);
        assertTrue(result.contains("task-"), "Thread name should contain the configured prefix");
    }
}
