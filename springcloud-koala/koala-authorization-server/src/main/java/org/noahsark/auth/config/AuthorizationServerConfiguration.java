package org.noahsark.auth.config;

import cn.hutool.json.JSONUtil;
import org.noahsark.auth.entity.User;
import org.noahsark.auth.service.CustomClientDetailsService;
import org.noahsark.auth.service.CustomerUserDetailsService;
import org.noahsark.common.result.Result;
import org.noahsark.common.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ?????????????????????
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


    /**
     * ??????????????????????????? ????????? password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * ??????userDetailsService?????????refresh_token????????????
     */
    @Autowired
    private CustomerUserDetailsService userDetailsService;

    /**
     * ?????????
     */
    @Autowired
    private DataSource dataSource;

    /**
     * ????????????token??????????????????????????????????????????
     * 1. Memory
     * 2. Jwt
     * 3. Jwk
     * 4. jdbc
     * 5. redis
     */
    @Autowired
    private TokenStore tokenStore;

    @Resource
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Bean
    public TokenStore tokenStore() {

        // Redis tokenStore
        return new RedisTokenStore(connectionFactory);

        // Jwt TokenStore
        /* return new JwtTokenStore(jwtAccessTokenConverter());*/
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // ?????????????????????
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        CustomClientDetailsService clientDetailsService = new CustomClientDetailsService(dataSource);
        clients.withClientDetails(clientDetailsService);

        // clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.tokenEnhancer(tokenEnhancerChain);

        //????????????????????????
        endpoints.authenticationManager(authenticationManager);
        //??????token????????????
        endpoints.tokenStore(tokenStore);
        //???????????????????????????????????????????????????
        // endpoints.exceptionTranslator(webResponseExceptionTranslator);
        //?????????refresh_token???????????????????????????userDetailsService
        endpoints.userDetailsService(userDetailsService);

    }

    /**
     * ??????????????????????????????token??????
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * ???classpath?????????????????????????????????(??????+??????)
     */
    @Bean
    public KeyPair keyPair() {

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("oauth2.jks"), "123456".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("oauth2");

        return keyPair;
    }

    /**
     * JWT????????????
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(2);
            User user = (User) authentication.getUserAuthentication().getPrincipal();
            map.put("userId", user.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }

    /**
     * ?????????????????????????????????
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.OK.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            Result result = Result.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }


}
