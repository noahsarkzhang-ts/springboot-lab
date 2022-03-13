package org.noahsark.auth.service;

import cn.hutool.core.collection.CollUtil;
import org.noahsark.common.constant.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 资源与角色匹配关系管理业务类
 * Created by macro on 2020/6/19.
 */
@Service
public class ResourceService {

    private Map<String, List<String>> resourceRolesMap;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("GET:/api/echo/**", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("GET:/api/user/**", CollUtil.toList("ADMIN", "USER"));
        redisTemplate.opsForHash().putAll(CacheConstants.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
