package org.noahsark.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 * Created by macro on 2020/6/19.
 */
@RestController
@RequestMapping("/app2")
public class HelloController {

    @Autowired
    private Environment env;

    @GetMapping("/hello")
    public String hello() {
        String port = env.getProperty("server.port");

        return "Hello Nginx:" + port;
    }

}
