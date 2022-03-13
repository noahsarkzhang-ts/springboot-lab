package org.noahsark.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Koala2ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Koala2ApiApplication.class, args);
    }

}
