package com.nagarro.productservice.controller;

import com.nagarro.productservice.client.UserClient;
import com.nagarro.productservice.dto.request.CartRequest;
import com.nagarro.productservice.dto.request.NewProduct;
import com.nagarro.productservice.dto.request.ProductUpdateRequest;
import com.nagarro.productservice.dto.response.CartResponse;
import com.nagarro.productservice.dto.response.ProductResponse;
import com.nagarro.productservice.model.Product;
import com.nagarro.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserClient userClient;

    @PostMapping()
    private ResponseEntity<Object> saveProduct(@RequestBody NewProduct product, @RequestParam String username){

        if(username.isEmpty())
            return ResponseEntity.badRequest().body("Please provide a valid username.");
        if(!userClient.isUserAdmin(username)){
            return ResponseEntity.badRequest().body(
                    "Action not allowed! Admin rights are needed to perform this action!");
        }
        ProductResponse productDetails = productService.saveNewProduct(product);
        log.info("Product Details: "+productDetails);
        return new ResponseEntity<>(
                "Product saved successfully with id: "+ productDetails.id(),
                HttpStatus.CREATED);
    }

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    private CartResponse addProductInCart(@RequestBody CartRequest cartRequest){

        return productService.addProductToCart(cartRequest);

    }
    @GetMapping()
    private ResponseEntity<Object> getAllProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    private ResponseEntity<Object> getProductById(@PathVariable long productId){

        return productService.fetchProductById(productId);

    }

    @DeleteMapping("/{productId}")
    private ResponseEntity<Object> deleteProductById(@PathVariable long productId, @RequestParam
                                                     String username){
        if(username == null)
            return ResponseEntity.badRequest().body("Please provide a valid username");
        if(!userClient.isUserAdmin(username)){
            return ResponseEntity.badRequest().body(
                    "Action not allowed! Admin rights are needed to perform this action!");
        }

        return productService.deleteProduct(productId);
    }

    @PutMapping()
    private ResponseEntity<Object> updateProduct(@RequestParam
                                                     String username, @RequestBody ProductUpdateRequest productUpdateRequest){
        if(!userClient.isUserAdmin(username)){
            return ResponseEntity.badRequest().body(
                    "Action not allowed! Admin rights are needed to perform this action!");
        }

        return productService.updateProduct(productUpdateRequest);
    }
}
