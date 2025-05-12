package com.nagarro.productdetailservice.service;

import com.nagarro.productdetailservice.dto.ProductResponse;
import com.nagarro.productdetailservice.dto.NewProduct;
import com.nagarro.productdetailservice.dto.ProductUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ProductDetailsService {

//    void sendNotification(String data);

    List<ProductResponse> fetchProducts();

    ProductResponse saveNewProduct(NewProduct productDetails);

    Optional<ProductResponse> fetchProductById(Long id);

    boolean deleteProduct(Long id);

    ProductResponse updateProductDetails(ProductUpdateRequest productUpdateRequest);

    double getProductPrice(Long id);
}
