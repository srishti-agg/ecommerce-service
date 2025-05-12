package com.nagarro.productservice.client;

import com.nagarro.productservice.dto.request.CartRequest;
import com.nagarro.productservice.dto.response.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {

    @PostMapping("/cart")
    CartResponse saveProductInCart(@RequestBody CartRequest cart);

}
