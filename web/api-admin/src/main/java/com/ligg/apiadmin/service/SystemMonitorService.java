package com.ligg.apiadmin.service;

import com.ligg.apiadmin.pojo.SystemInfoVo;
import com.ligg.apiadmin.pojo.SystemStatusVo;

/**
 * @Author Ligg
 * @Time 2025/10/26
 **/
public interface SystemMonitorService {

    /**
     * 获取系统状态
     *
     * @return 系统状态
     */
    SystemStatusVo getSystemStatus();

    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    SystemInfoVo getSystemInfo();
}
