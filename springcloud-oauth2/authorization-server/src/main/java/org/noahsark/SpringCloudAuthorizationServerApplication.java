package org.noahsark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringCloudAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( SpringCloudAuthorizationServerApplication.class, args );
    }

}
