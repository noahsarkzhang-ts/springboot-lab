package org.noahsark.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * ShardingJdbc App
 *
 * @author zhangxt
 * @date 2022/04/19 10:09
 **/
@SpringBootApplication
@MapperScan("org.noahsark.shardingjdbc.mapper")
public class SpringbootShardingJdbcApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShardingJdbcApp.class, args);
    }
}
