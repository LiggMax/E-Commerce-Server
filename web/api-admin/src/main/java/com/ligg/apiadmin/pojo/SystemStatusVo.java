package com.ligg.apiadmin.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
@Data
public class SystemStatusVo {

    /**
     * 系统启动时间 (秒)
     */
    private long systemUptime;

    /**
     * CPU 使用率 (%)
     */
    private double cpuUsage;

    /**
     * CPU 温度 (°C)
     */
    private double cpuTemperature;

    /**
     * CPU型号
     */
    private String cpuModel;

    /**
     * 总内存 (GB)
     */
    private double totalMemory;

    /**
     * 已用内存 (GB)
     */
    private double usedMemory;

    /**
     * 总磁盘 (GB)
     */
    private double totalDisk;

    /**
     * 已用磁盘 (GB)
     */
    private double usedDisk;

    /**
     * 网络接口信息
     */
    private List<NetworkInfo> networkInfo;

    @Data
    public static class NetworkInfo {
        /**
         * 网络接口名称
         */
        private String name;

        /**
         * MAC 地址
         */
        private String macAddr;

        /**
         * IPv4 地址
         */
        private String[] ipv4Addr;

        /**
         * 已发送数据量 (bytes)
         */
        private long bytesSent;

        /**
         * 已接收数据量 (bytes)
         */
        private long bytesRecv;

        /**
         * 上传速度 (bytes per second)
         */
        private double uploadSpeed;

        /**
         * 下载速度 (bytes per second)
         */
        private double downloadSpeed;
    }
}
