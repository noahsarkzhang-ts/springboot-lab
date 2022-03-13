package org.noahsark.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 授权服务器启动类
 * @author zhangxt
 * @date 2022/03/12 17:56
 **/
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class KoalaAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( KoalaAuthorizationServerApplication.class, args );
    }

}
