package org.noahsark.gateway.controller;

import org.noahsark.common.result.Result;
import org.noahsark.common.result.ResultCode;
import org.noahsark.gateway.bean.AccessToken;
import org.noahsark.gateway.bean.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆 Controller
 *
 * @author zhangxt
 * @date 2022/03/14 18:43
 **/
@RestController
@RequestMapping("/gw")
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 存放 ClientId & Secret 信息
     */
    private static final Map<String, String> CLIENT_MAP = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * 存放登陆的回调地址，格式：uuid--> redirectUrl
     */
    private static Map<String, LoginRequest> loginMap = new HashMap<>();

    /**
     * 存放登陆的 accessToken
     */
    private static Map<String, AccessToken> tokenMap = new HashMap<>();

    static {
        CLIENT_MAP.put("app1", "123456");
        CLIENT_MAP.put("app2", "123456");
    }

    @GetMapping("/login")
    public Mono<Void> login(ServerHttpResponse response, @RequestParam Map<String, String> parameters) {
        /**
         * 参数包括：
         * 1. clientId: 客户端 id
         * 2. redirectUrl: 回调地址
         * 3. uuid: 标识一次登陆请求
         */
        String uuid = parameters.get("uuid");
        String clientId = parameters.get("clientId");
        String redirectUri = parameters.get("redirectUri");

        LoginRequest loginRequest = new LoginRequest(uuid, clientId, redirectUri);
        loginMap.put(parameters.get("uuid"), loginRequest);

        LOGGER.info("receive login request:{}", loginRequest);

        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:8080/cas/oauth/authorize?")
                .append("client_id=").append(clientId)
                .append("&redirect_uri=").append("http://localhost:8081/gw/login-callback")
                .append("&response_type=").append("code")
                .append("&state=").append(uuid);


        return Mono.fromRunnable(() -> {
            response.setStatusCode(HttpStatus.FOUND);
            response.getHeaders().setLocation(URI.create(builder.toString()));
        });

    }

    @GetMapping("/login-callback")
    public Mono<Void> loginCallback(ServerHttpResponse response, @RequestParam Map<String, String> parameters) {

        String code = parameters.get("code");
        String state = parameters.get("state");

        LoginRequest loginRequest = loginMap.get(state);

        LOGGER.info("login callback:{}", loginRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("grant_type", "authorization_code");
        map.add("client_id", loginRequest.getClientId());
        map.add("client_secret", CLIENT_MAP.get(loginRequest.getClientId()));
        map.add("redirect_uri", "http://localhost:8081/gw/login-callback");

        String url = "http://koala-cas-server/cas/oauth/token";

        ResponseEntity<Result<AccessToken>> tokenResponse = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(map, headers), new ParameterizedTypeReference<Result<AccessToken>>() {
                });

        LOGGER.info("token response:{}", tokenResponse.getBody());
        StringBuilder builder = new StringBuilder();

        if (tokenResponse != null && tokenResponse.getStatusCode().equals(HttpStatus.OK)
                && tokenResponse.getBody().getCode().equals(ResultCode.SUCCESS.getCode())) {

            tokenMap.put(loginRequest.getUuid(), tokenResponse.getBody().getData());

            builder.append(loginRequest.getRedirectUri()).append("/?")
                    .append("uuid=").append(loginRequest.getUuid());

            return Mono.fromRunnable(() -> {
                response.setStatusCode(HttpStatus.FOUND);
                response.getHeaders().setLocation(URI.create(builder.toString()));
            });
        }

        builder.append(loginRequest.getRedirectUri()).append("/?error=404");

        return Mono.fromRunnable(() -> {
            response.setStatusCode(HttpStatus.FOUND);
            response.getHeaders().setLocation(URI.create(builder.toString()));
        });

    }

    @GetMapping("/token")
    public Mono<Result<AccessToken>> token(@RequestParam Map<String, String> parameters) {

        String uuid = parameters.get("uuid");

        AccessToken accessToken = tokenMap.get(uuid);
        Result<AccessToken> result = Result.success(accessToken);

        return Mono.just(result);
    }

}
