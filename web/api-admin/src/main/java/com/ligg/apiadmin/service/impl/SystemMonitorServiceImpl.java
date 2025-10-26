package com.ligg.apiadmin.service.impl;

import com.ligg.apiadmin.pojo.SystemInfoVo;
import com.ligg.apiadmin.service.SystemMonitorService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.io.File;

/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
@Service
public class SystemMonitorServiceImpl implements SystemMonitorService {

    private final SystemInfo systemInfo = new SystemInfo();
    private final CentralProcessor processor = systemInfo.getHardware().getProcessor();
    private long[] prevTicks = processor.getSystemCpuLoadTicks();


    @Override
    public SystemInfoVo getSystemInfo() {
        SystemInfoVo info = new SystemInfoVo();

        // CPU 使用率
        long[] currentTicks = processor.getSystemCpuLoadTicks();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = currentTicks; // 更新上一次的 ticks

        info.setCpuUsage(round(cpuLoad, 2));

        // 内存信息
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        double totalMemory = memory.getTotal() / 1024.0 / 1024 / 1024;
        double usedMemory = (memory.getTotal() - memory.getAvailable()) / 1024.0 / 1024 / 1024;
        info.setTotalMemory(round(totalMemory, 2));
        info.setUsedMemory(round(usedMemory, 2));

        // 磁盘信息（取系统根目录）
        File disk = new File("/");
        double totalDisk = disk.getTotalSpace() / 1024.0 / 1024 / 1024;
        double usedDisk = (disk.getTotalSpace() - disk.getFreeSpace()) / 1024.0 / 1024 / 1024;
        info.setTotalDisk(round(totalDisk, 2));
        info.setUsedDisk(round(usedDisk, 2));

        return info;
    }

    private double round(double value, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(value * scale) / scale;
    }
}
