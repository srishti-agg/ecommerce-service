package com.nagarro.orderservice.client;

import com.nagarro.orderservice.dto.request.UserCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {

    @GetMapping("/cart/{userId}")
    List<UserCart> getUserCartDetails(@PathVariable String userId);

    @DeleteMapping("/cart/{userId}")
    ResponseEntity<Object> emptyUserCart(@PathVariable String userId);

}
