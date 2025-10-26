/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
package com.ligg.apiadmin.controller;

import com.ligg.apiadmin.pojo.SystemInfoVo;
import com.ligg.apiadmin.service.SystemMonitorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Tag(name = "系统接口")
@RestController
@RequestMapping("/api/admin/system")
@RequiredArgsConstructor
public class SystemController {

    private final SystemMonitorService systemMonitorService;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSystemInfo() {
        SseEmitter emitter = new SseEmitter(0L); // 不超时

        executor.execute(() -> {
            try {
                while (true) {
                    SystemInfoVo info = systemMonitorService.getSystemInfo();
                    emitter.send(SseEmitter.event()
                            .name("system-info")
                            .data(info));
                    Thread.sleep(1000); // 每 1 秒推送一次
                }
            } catch (IOException | InterruptedException e) {
                emitter.complete();
            }
        });

        return emitter;
    }
}
