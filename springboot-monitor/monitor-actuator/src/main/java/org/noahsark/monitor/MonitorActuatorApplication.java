package org.noahsark.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@SpringBootApplication
public class MonitorActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorActuatorApplication.class, args);
    }
}
