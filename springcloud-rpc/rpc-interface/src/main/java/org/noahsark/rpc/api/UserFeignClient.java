package org.noahsark.rpc.api;

import org.noahsark.rpc.common.domain.Result;
import org.noahsark.rpc.common.domain.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * USer Feign Client
 *
 * @author zhangxt
 * @date 2022/04/13 14:59
 **/
@FeignClient(value = "provider-service")
public interface UserFeignClient {

    @GetMapping(value = "/users/name")
    String name();

    @GetMapping("/users/online")
    UserDTO currentUser();

    @GetMapping("/users/query")
    Result<UserDTO> query();
}
