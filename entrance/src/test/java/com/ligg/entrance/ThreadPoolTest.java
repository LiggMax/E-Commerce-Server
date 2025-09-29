package com.ligg.entrance;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Ligg
 * @Time 2025/9/28
 **/
@Slf4j
@SpringBootTest
public class ThreadPoolTest {

    @Qualifier("fileTaskExecutor")
    @Autowired
    private TaskExecutor fileOperationExecutor;

    /**
     * 测试文件操作线程池处理异步任务
     */
    @Test
    public void testFileOperationExecutorForAsyncFileTasks() throws InterruptedException {
        assertNotNull(fileOperationExecutor);
        assertInstanceOf(ThreadPoolTaskExecutor.class, fileOperationExecutor);

        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) fileOperationExecutor;
        // 验证文件操作线程池配置
        assertEquals("file-io-task-", executor.getThreadNamePrefix());
        assertEquals(Runtime.getRuntime().availableProcessors() * 2, executor.getCorePoolSize());
        assertEquals(Runtime.getRuntime().availableProcessors() * 4, executor.getMaxPoolSize());
        assertEquals(100, executor.getQueueCapacity());

        // 模拟文件操作任务
        int taskCount = 10;
        CountDownLatch latch = new CountDownLatch(taskCount);
        long startTime = System.currentTimeMillis();

        // 提交多个模拟文件操作任务到线程池
        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            executor.execute(() -> {
                try {
                    // 模拟文件读写操作，耗时50-200ms不等
                    Thread.sleep(50 + (int) (Math.random() * 150));
                    log.info("File task {} completed by {}", taskId, Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        // 等待所有任务完成，设置合理的超时时间
        boolean finished = latch.await(10, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();

        assertTrue(finished, "All file operation tasks should complete within 10 seconds");
        log.info("在 {} 毫秒内完成 {} 个文件任务", taskCount, endTime - startTime);
    }

    /**
     * 测试文件操作线程池在高并发情况下的表现
     */
    @Test
    public void testFileOperationExecutorUnderHighConcurrency() throws InterruptedException {
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) fileOperationExecutor;

        // 提交超过核心线程数的任务，测试线程池扩容能力
        int taskCount = executor.getCorePoolSize() + 5;
        CountDownLatch latch = new CountDownLatch(taskCount);

        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            executor.execute(() -> {
                try {
                    // 模拟耗时的文件操作
                    Thread.sleep(300);
                    log.info("并发文件任务 {} 由 {} 执行", taskId, Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        // 验证所有任务都能完成
        boolean finished = latch.await(15, TimeUnit.SECONDS);
        assertTrue(finished, "所有并发文件任务应在 15 秒内完成");
    }
}
