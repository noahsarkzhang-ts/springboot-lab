package org.noahsark.canal.main;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.noahsark.canal.client.SimpleCanalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 直连 Canal
 *
 * @author zhangxt
 * @date 2022/04/11 11:05
 **/
//@Component
public class SimpleCanalCommandRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCanalCommandRunner.class);

    @Override
    public void run(String... args) throws Exception {
        // 根据ip，直接创建链接，无HA的功能
        String destination = "example";
        String ip = "127.0.0.1";
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(ip, 11111),
                destination,
                "canal",
                "canal");

        final SimpleCanalClient client = new SimpleCanalClient(destination);
        client.setConnector(connector);
        client.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LOGGER.info("## stop the canal client");
                client.stop();
            } catch (Throwable e) {
                LOGGER.warn("##something goes wrong when stopping canal:", e);
            } finally {
                LOGGER.info("## canal client is down.");
            }
        }));
    }
}
