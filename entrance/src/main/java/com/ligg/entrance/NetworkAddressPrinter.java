package com.ligg.entrance;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author Ligg
 * @Time 2025/9/17
 **/
@Component
public class NetworkAddressPrinter implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            System.out.println("===============================================");
            System.out.println("局域网访问地址: http://" + hostAddress + ":8080");
            System.out.println("本地访问地址: http://localhost:8080");
            System.out.println("===============================================");
        } catch (UnknownHostException e) {
            System.out.println("无法获取本机IP地址");
        }
    }
}
