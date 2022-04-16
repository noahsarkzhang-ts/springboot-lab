package org.noahsark.canal.client;

import org.slf4j.MDC;
import org.springframework.util.Assert;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;

/**
 * Canal Client 骨架类
 *
 * @author zhangxt
 * @date 2022/04/11 09:41
 **/
public class BaseCanalClient extends CanalOperation {

    public BaseCanalClient(String destination) {
        this(destination, null);
    }

    public BaseCanalClient(String destination, CanalConnector connector) {
        this.destination = destination;
        this.connector = connector;
    }

    public void start() {
        Assert.notNull(connector, "connector is null");
        thread = new Thread(this::process);

        thread.setUncaughtExceptionHandler(handler);
        running = true;
        thread.start();
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }

        MDC.remove("destination");
    }

    protected void process() {
        int batchSize = 5 * 1024;
        while (running) {
            try {
                MDC.put("destination", destination);
                connector.connect();
                connector.subscribe();
                while (running) {
                    Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        // try {
                        // Thread.sleep(1000);
                        // } catch (InterruptedException e) {
                        // }
                    } else {
                        printSummary(message, batchId, size);
                        printEntry(message.getEntries());
                    }

                    if (batchId != -1) {
                        connector.ack(batchId); // 提交确认
                    }
                }
            } catch (Throwable e) {
                logger.error("process error!", e);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e1) {
                    // ignore
                }

                connector.rollback(); // 处理失败, 回滚数据
            } finally {
                connector.disconnect();
                MDC.remove("destination");
            }
        }
    }

}
