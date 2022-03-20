package org.noahsark.gateway.sentinel;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.noahsark.common.result.Result;
import org.noahsark.common.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;

/**
 * Sentinel config
 * @author zhangxt
 * @date 2022/03/14 12:53
 **/
@Configuration
public class SentinelConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentinelConfig.class);

    /**
     * 自定义限流处理器
     */
    @PostConstruct
    public void initBlockHandlers() {
        BlockRequestHandler blockHandler = (serverWebExchange, throwable) -> {

            LOGGER.info("Sentinel Block request!!");

            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(JSONUtil.toJsonStr(Result.failed(ResultCode.FLOW_LIMITING)));
        };

        GatewayCallbackManager.setBlockHandler(blockHandler);
    }
}
