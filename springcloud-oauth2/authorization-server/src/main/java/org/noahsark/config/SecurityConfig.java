package org.noahsark.config;


import org.noahsark.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 自定义 serDetailsService 类
    @Autowired
    private MyUserDetailsService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 自定义 serDetailsService 类
        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
            // 对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }

            // 对密码进行判断匹配
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                boolean res = s.equals(encode);
                return res;
            }
        });

    }

    /**
     * 返回 AuthenticationManager 类，在 Oauth2 中会复用该类进行用户的认证。
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 用于 Http Basic 认证方式中，用户名/密码的验证
     * 在 oauth2 中 clientId/secret 的验证，此时不用密码不用加密
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return Objects.equals(charSequence.toString(), s);
            }
        };
    }

}
