package org.noahsark.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Cache App
 *
 * @author zhangxt
 * @date 2022/04/19 10:09
 **/
@SpringBootApplication
@MapperScan("org.noahsark.cache.mapper")
@EnableCaching
public class SpringbootCacheApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheApp.class, args);
    }
}
