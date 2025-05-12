package com.nagarro.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/user")
    boolean isUserAdmin(@RequestParam String username);
}
