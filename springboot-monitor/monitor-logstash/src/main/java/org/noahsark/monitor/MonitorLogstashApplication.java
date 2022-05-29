package org.noahsark.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MonitorLogstashApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorLogstashApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MonitorLogstashApplication.class, args);
    }

    @Scheduled(cron = "*/15 * * * * ?")
    public void log() {
        LOGGER.info("logstash test!");
    }
}
