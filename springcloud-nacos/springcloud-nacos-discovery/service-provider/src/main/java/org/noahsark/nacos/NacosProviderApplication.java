package org.noahsark.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @RestController
    @RequestMapping("/services")
    class EchoController {
        private Logger logger = LoggerFactory.getLogger(EchoController.class);

        @RequestMapping(value = "/hello/{str}", method = RequestMethod.GET)
        public String echo(@PathVariable String str) {
            logger.info("Receive a request:{}", str);

            Random random = new Random();
            int seelepNum = random.nextInt(1000);

            try {
                TimeUnit.MILLISECONDS.sleep(seelepNum);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            return "Hello Nacos Discovery: " + str;
        }
    }
}
