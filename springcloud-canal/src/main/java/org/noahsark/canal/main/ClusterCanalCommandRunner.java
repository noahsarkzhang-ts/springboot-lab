package org.noahsark.canal.main;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.noahsark.canal.client.ClusterCanalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Canal HA
 *
 * @author zhangxt
 * @date 2022/04/11 13:44
 **/
@Component
public class ClusterCanalCommandRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCanalCommandRunner.class);

    @Override
    public void run(String... args) throws Exception {
        String destination = "example";

        // 基于zookeeper动态获取canal server的地址，建立链接，其中一台server发生crash，可以支持failover
        CanalConnector connector = CanalConnectors.newClusterConnector("192.168.7.115:2181", destination, "canal", "canal");

        final ClusterCanalClient client = new ClusterCanalClient(destination);
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
