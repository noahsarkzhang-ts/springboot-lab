package org.noahsark.config;

import org.noahsark.entity.User;
import org.noahsark.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
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
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


    /**
     * 注入权限验证控制器 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 注入userDetailsService，开启refresh_token需要用到
     */
    @Autowired
    private MyUserDetailsService userDetailsService;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 设置保存token的方式，一共有五种，分别是：
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
        // 允许静表单访问
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

        //开启密码授权类型
        endpoints.authenticationManager(authenticationManager);
        //配置token存储方式
        endpoints.tokenStore(tokenStore);
        //自定义登录或者鉴权失败时的返回信息
        // endpoints.exceptionTranslator(webResponseExceptionTranslator);
        //要使用refresh_token的话，需要额外配置userDetailsService
        endpoints.userDetailsService(userDetailsService);

    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {

        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("oauth2.jks"), "123456".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("oauth2");

        return keyPair;
    }

    /**
     * JWT内容增强
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


}
