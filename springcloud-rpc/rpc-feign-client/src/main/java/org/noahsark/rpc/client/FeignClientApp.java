package org.noahsark.rpc.client;

import org.noahsark.rpc.api.UserFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign Client
 *
 * @author zhangxt
 * @date 2022/04/13 14:21
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {UserFeignClient.class})
public class FeignClientApp {

    public static void main(String[] args) {
        SpringApplication.run(FeignClientApp.class, args);
    }
}
