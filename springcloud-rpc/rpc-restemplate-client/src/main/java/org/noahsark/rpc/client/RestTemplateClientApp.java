package org.noahsark.rpc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * RestTemplate Client
 *
 * @author zhangxt
 * @date 2022/04/13 14:21
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class RestTemplateClientApp {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateClientApp.class, args);
    }
}
