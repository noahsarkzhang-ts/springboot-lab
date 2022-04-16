package org.noahsark.rpc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Rpc provider APP
 *
 * @author zhangxt
 * @date 2022/04/13 13:49
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class RpcProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(RpcProviderApp.class, args);
    }
}
