package org.noahsark.cache.controller;

import org.noahsark.cache.common.Result;
import org.noahsark.cache.model.User;
import org.noahsark.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User Controller
 *
 * @author zhangxt
 * @date 2022/04/19 10:23
 **/

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Result<List<User>> getUsers() {
        List<User> users = userService.getUsers();

        return Result.success(users);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<User> getUser(@PathVariable("id") Integer id) {
        User user = userService.getUser(id);

        return Result.success(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result<User> save(@RequestBody User user) {
        userService.save(user);

        return Result.success(user);

    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody User user) {
        userService.update(user);

        return Result.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Void> delete(@PathVariable("id") Integer id) {
        userService.delete(id);

        return Result.success();
    }
}
