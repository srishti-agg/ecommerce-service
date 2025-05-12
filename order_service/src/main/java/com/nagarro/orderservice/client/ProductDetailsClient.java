package com.nagarro.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-DETAILS-SERVICE")
public interface ProductDetailsClient {

    @GetMapping("/products/price/{id}")
    double getProductPriceById(@PathVariable Long id);
}
