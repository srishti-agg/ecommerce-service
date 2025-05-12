package com.nagarro.productservice.service;

import com.nagarro.productservice.dto.request.CartRequest;
import com.nagarro.productservice.dto.request.NewProduct;
import com.nagarro.productservice.dto.request.ProductUpdateRequest;
import com.nagarro.productservice.dto.response.CartResponse;
import com.nagarro.productservice.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ProductResponse saveNewProduct(NewProduct product);

    ResponseEntity<Object> getAllProducts();

    ResponseEntity<Object> fetchProductById(long productId);

    CartResponse addProductToCart(CartRequest cartRequest);

    ResponseEntity<Object> deleteProduct(long productId);

    ResponseEntity<Object> updateProduct(ProductUpdateRequest productUpdateRequest);
}
