package org.noahsark.rpc.client.controller;

import org.noahsark.rpc.common.domain.Result;
import org.noahsark.rpc.common.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String name() {
        return restTemplate.getForObject("http://provider-service/users/name", String.class);
    }

    @GetMapping("/online")
    public UserDTO currentUser() {
        return restTemplate.getForObject("http://provider-service/users/online", UserDTO.class);
    }

    @GetMapping("/query")
    public Result<UserDTO> query() {

        String url = "http://provider-service/users/query";

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        ResponseEntity<Result<UserDTO>> userResponse = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity(map,headers), new ParameterizedTypeReference<Result<UserDTO>>() {
                });

        return userResponse.getBody();
    }

}
