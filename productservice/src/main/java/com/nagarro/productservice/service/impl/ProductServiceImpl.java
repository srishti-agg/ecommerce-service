package com.nagarro.productservice.service.impl;

import com.nagarro.productservice.client.CartClient;
import com.nagarro.productservice.client.InventoryClient;
import com.nagarro.productservice.client.ProductDetailsClient;
import com.nagarro.productservice.dto.request.CartRequest;
import com.nagarro.productservice.dto.request.InventoryDetails;
import com.nagarro.productservice.dto.request.NewProduct;
import com.nagarro.productservice.dto.request.ProductUpdateRequest;
import com.nagarro.productservice.dto.response.CartResponse;
import com.nagarro.productservice.dto.response.ProductResponse;
import com.nagarro.productservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDetailsClient productDetailsClient;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private CartClient cartClient;

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod =
            "fallbackSaveNewProductResponse" )
    public ProductResponse saveNewProduct(NewProduct product) {

        ProductResponse response = productDetailsClient.saveProduct(product);
        System.out.println(product.toString());
        InventoryDetails inventoryRequestDTO = new InventoryDetails(response.id(), product.product_count());
        inventoryClient.saveInventoryDetails(inventoryRequestDTO);
        return response;
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackResponse" )
    public ResponseEntity<Object> getAllProducts() {
        return productDetailsClient.getProduct();
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackResponse" )
    public ResponseEntity<Object> fetchProductById(long productId) {

        log.info("Fetching product details from product details service");
        Object res = productDetailsClient.
                findProductDetailsById(productId);
        log.info("Received following response from product details service: {}", res);

        if(res==null){
            return ResponseEntity.badRequest().body("No product exists with id: "+productId);
        }
        return ResponseEntity.ok().body(res);
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackCartResponse" )
    public CartResponse addProductToCart(CartRequest cartRequest) {
        log.info(cartRequest.toString());
        return cartClient.saveProductInCart(cartRequest);
    }

    @Override
    public ResponseEntity<Object> deleteProduct(long productId) {

        if(!productDetailsClient.deleteProduct(productId))
             return ResponseEntity.badRequest().body("No product exists with the " +
                "mentioned product id!");

        return ResponseEntity.ok().body("Product deleted successfully!");
    }

    @Override
    public ResponseEntity<Object> updateProduct(ProductUpdateRequest productUpdateRequest) {

        return productDetailsClient.updateProductDetails(productUpdateRequest);
    }

    private ProductResponse fallbackSaveNewProductResponse(Throwable throwable){
        return new ProductResponse(0L,0,"Invalid request",
                "Invalid request","Invalid request","Invalid request","Invalid request");
    }

    private ResponseEntity<Object> fallbackResponse(Throwable throwable){

        return ResponseEntity.internalServerError().
                body("Service is unavailable right now!");
    }

    private CartResponse fallbackCartResponse(Throwable throwable){

        return new CartResponse();
    }
}
