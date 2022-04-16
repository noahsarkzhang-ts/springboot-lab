package org.noahsark.rpc.client.controller;

import org.noahsark.rpc.api.UserFeignClient;
import org.noahsark.rpc.common.domain.Result;
import org.noahsark.rpc.common.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller
 *
 * @author zhangxt
 * @date 2022/04/13 14:26
 **/
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String name() {
        return userFeignClient.name();
    }

    @GetMapping("/online")
    public UserDTO currentUser() {
        return userFeignClient.currentUser();
    }

    @GetMapping("/query")
    public Result<UserDTO> query() {

        return userFeignClient.query();
    }

}
