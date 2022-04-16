package org.noahsark.rpc.provider.controller;

import com.google.common.collect.Lists;
import org.noahsark.rpc.common.domain.Result;
import org.noahsark.rpc.common.domain.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取登录用户信息接口
 * Created by macro on 2020/6/19.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/name")
    public String name() {
        return "admin";
    }

    @GetMapping("/online")
    public UserDTO currentUser() {

        UserDTO user = new UserDTO();

        user.setId(1000L);
        user.setUsername("admin");
        user.setRoles(Lists.newArrayList("admin"));

        return user;
    }

    @GetMapping("/query")
    public Result<UserDTO> query() {

        UserDTO user = new UserDTO();

        user.setId(1000L);
        user.setUsername("admin");
        user.setRoles(Lists.newArrayList("admin"));

        return Result.success(user);
    }


}
