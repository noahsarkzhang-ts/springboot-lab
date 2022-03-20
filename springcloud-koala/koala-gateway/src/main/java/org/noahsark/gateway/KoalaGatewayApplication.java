package org.noahsark.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关程序
 *
 * @author zhangxt
 * @date 2022/03/01 20:50
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class KoalaGatewayApplication {

    public static void main(String[] args) {

        System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(KoalaGatewayApplication.class, args);
    }
}
