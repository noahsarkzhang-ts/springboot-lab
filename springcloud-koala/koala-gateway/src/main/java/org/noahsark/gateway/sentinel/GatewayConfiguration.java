package org.noahsark.gateway.sentinel;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.noahsark.common.result.Result;
import org.noahsark.common.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Sentinel config
 * @author zhangxt
 * @date 2022/03/14 12:53
 **/
@Configuration
public class GatewayConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayConfiguration.class);

    @PostConstruct
    public void doInit() {
        initBlockHandlers();
        initCustomizedApis();
        initGatewayRules();
    }

    /**
     * 自定义 api 组
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("some_customized_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/configs/**"));
                    add(new ApiPathPredicateItem().setPattern("/services/**")
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});

        definitions.add(api1);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * 自定义网关 Rules
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule(" koala-oauth")
                .setCount(10)
                .setIntervalSec(1)
        );

        rules.add(new GatewayFlowRule("koala-oauth2-api")
                .setCount(2)
                .setIntervalSec(2)
                .setBurst(2)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
                )
        );

        rules.add(new GatewayFlowRule("some_customized_api")
                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME)
                .setCount(5)
                .setIntervalSec(1)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
                        .setFieldName("pn")
                )
        );

        GatewayRuleManager.loadRules(rules);
    }

    /**
     * 自定义限流处理器
     */
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
