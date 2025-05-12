package com.nagarro.productservice.client;

import com.nagarro.productservice.dto.request.NewProduct;
import com.nagarro.productservice.dto.request.ProductUpdateRequest;
import com.nagarro.productservice.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT-DETAILS-SERVICE")
public interface ProductDetailsClient {

    @PostMapping("/products")
    ProductResponse saveProduct(NewProduct product);

    @GetMapping("/products")
    ResponseEntity<Object> getProduct();

    @GetMapping("/products/{id}")
    Object findProductDetailsById(@PathVariable Long id);

    @DeleteMapping("/products/{id}")
    boolean deleteProduct(@PathVariable Long id);

    @PutMapping("/products")
    ResponseEntity<Object> updateProductDetails(
            ProductUpdateRequest productUpdateRequest);
}
