package com.ligg.apiadmin.pojo;

import lombok.Data;

/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
@Data
public class SystemInfoVo {
    /**
     * CPU 使用率 (%)
     */
    private double cpuUsage;

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
}
