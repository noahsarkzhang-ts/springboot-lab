package org.noahsark.cache.service;

import org.noahsark.cache.mapper.UserMapper;
import org.noahsark.cache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service
 *
 * @author zhangxt
 * @date 2022/04/19 20:03
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers() {
        List<User> users = userMapper.getAll();
        return users;
    }

    @Cacheable(value = "users", key = "#id")
    public User getUser(Integer id) {

        User user = userMapper.getUser(id);

        return user;
    }

    @CachePut(value = "users", key = "#user.id")
    public User save(User user) {
        userMapper.insert(user);

        return user;
    }

    @CachePut(value = "users", key = "#user.id")
    public User update(User user) {
        userMapper.update(user);

        return user;
    }

    @CacheEvict(value = "users", key = "#id")
    public void delete(Integer id) {
        userMapper.delete(id);
    }
}
