/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
package com.ligg.apiadmin.controller;

import com.ligg.apiadmin.pojo.vo.SystemInfoVo;
import com.ligg.apiadmin.pojo.vo.SystemStatusVo;
import com.ligg.apiadmin.service.SystemMonitorService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "获取系统状态", description = "CPU使用率,内存使用率,磁盘使用率")
    @GetMapping(value = "/status", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSystemInfo() {
        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {
                while (true) {
                    SystemStatusVo info = systemMonitorService.getSystemStatus();
                    emitter.send(SseEmitter.event()
                            .name("system-info")
                            .data(info));
                    Thread.sleep(5000); // 每秒推送次数
                }
            } catch (IOException | InterruptedException e) {
                emitter.complete();
            }
        });

        return emitter;
    }

    @GetMapping("/info")
    @Operation(summary = "获取系统信息", description = "总用户数,今日订单量,总收入数,系统负载")
    public Response<SystemInfoVo> getSystemInfo() {
        return Response.success(BusinessStates.SUCCESS,systemMonitorService.getSystemInfo());
    }
}
