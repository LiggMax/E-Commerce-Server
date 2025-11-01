package com.ligg.apiadmin.service.impl;

import com.ligg.apiadmin.pojo.vo.SystemInfoVo;
import com.ligg.apiadmin.pojo.vo.SystemStatusVo;
import com.ligg.apiadmin.service.SystemMonitorService;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.mapper.order.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
@Service
@RequiredArgsConstructor
public class SystemMonitorServiceImpl implements SystemMonitorService {

    private final UserMapper userMapper;

    private final OrderMapper orderMapper;


    private final SystemInfo systemInfo = new SystemInfo();
    private final CentralProcessor processor = systemInfo.getHardware().getProcessor();
    private final Sensors sensors = systemInfo.getHardware().getSensors();
    private final OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
    private long[] prevTicks = processor.getSystemCpuLoadTicks();

    // 用于存储上一次网络流量数据
    private final Map<String, NetworkData> networkDataMap = new HashMap<>();

    // 网络数据存储类
    @AllArgsConstructor
    private static class NetworkData {
        long bytesSent;
        long bytesRecv;
        long timestamp;
    }

    /**
     * 获取系统状态
     *
     * @return 系统状态
     */
    @Override
    public SystemStatusVo getSystemStatus() {
        SystemStatusVo info = new SystemStatusVo();

        //系统启动时间
        long systemBootTime = operatingSystem.getSystemUptime();
        info.setSystemUptime(systemBootTime);

        // CPU
        long[] currentTicks = processor.getSystemCpuLoadTicks();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = currentTicks; // 更新上一次的 ticks
        info.setCpuUsage(round(cpuLoad, 2));//使用率
        String cpuName = processor.getProcessorIdentifier().getName();
        info.setCpuModel(cpuName);//型号
        double cpuTemperature = sensors.getCpuTemperature();
        info.setCpuTemperature(round(cpuTemperature, 2));

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

        // 网络信息
        List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();
        List<SystemStatusVo.NetworkInfo> networkInfos = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        for (NetworkIF networkIF : networkIFs) {
            SystemStatusVo.NetworkInfo netInfo = new SystemStatusVo.NetworkInfo();
            netInfo.setName(networkIF.getName());
            netInfo.setMacAddr(networkIF.getMacaddr());
            netInfo.setIpv4Addr(networkIF.getIPv4addr());
            netInfo.setBytesSent(networkIF.getBytesSent());
            netInfo.setBytesRecv(networkIF.getBytesRecv());

            // 计算实时网速
            String interfaceName = networkIF.getName();
            long bytesSent = networkIF.getBytesSent();
            long bytesRecv = networkIF.getBytesRecv();

            NetworkData previousData = networkDataMap.get(interfaceName);
            if (previousData != null) {
                long timeDelta = currentTime - previousData.timestamp;
                if (timeDelta > 0) {
                    // 计算上传和下载速度 (bytes per second)
                    long sentDelta = bytesSent - previousData.bytesSent;
                    long recvDelta = bytesRecv - previousData.bytesRecv;

                    double uploadSpeed = (sentDelta * 1000.0) / timeDelta;  // bytes per second
                    double downloadSpeed = (recvDelta * 1000.0) / timeDelta;  // bytes per second

                    netInfo.setUploadSpeed(uploadSpeed);
                    netInfo.setDownloadSpeed(downloadSpeed);
                }
            }

            // 更新存储的数据
            networkDataMap.put(interfaceName, new NetworkData(bytesSent, bytesRecv, currentTime));
            networkInfos.add(netInfo);
        }
        info.setNetworkInfo(networkInfos);

        return info;
    }

    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    @Override
    public SystemInfoVo getSystemInfo() {
        SystemInfoVo systemInfoVo = new SystemInfoVo();
        int orderCount = orderMapper.getTodayOrderCount();
        systemInfoVo.setTodayOrderCount(orderCount);
        Long userCount = userMapper.selectCount(null);
        systemInfoVo.setUserCount(userCount);
        //获取已完成订单总金额
        BigDecimal orderTotalAmount = orderMapper.getOrderTotalAmount(OrderStatus.PAID);
        systemInfoVo.setOrderTotalAmount(orderTotalAmount);
        return systemInfoVo;
    }

    private double round(double value, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(value * scale) / scale;
    }
}
