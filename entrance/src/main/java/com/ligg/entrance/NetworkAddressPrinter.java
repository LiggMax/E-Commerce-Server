/**
 * @Author Ligg
 * @Time 2025/9/17
 **/
package com.ligg.entrance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class NetworkAddressPrinter implements ApplicationRunner {

    @Value("${server.port}")
    private String port;

    @Override
    public void run(ApplicationArguments args) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            System.out.println("===============================================");
            System.out.println("局域网访问地址: http://" + hostAddress + ":" + this.port);
            System.out.println("本地访问地址: http://localhost:" + this.port);
            System.out.println("在线文档地址: http://localhost:" + this.port + "/doc.html");
            System.out.println("===============================================");
        } catch (UnknownHostException e) {
            System.out.println("无法获取本机IP地址");
        }
    }
}
