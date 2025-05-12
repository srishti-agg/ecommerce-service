package com.nagarro.productdetailservice.controller;

import com.nagarro.productdetailservice.dto.ProductResponse;
import com.nagarro.productdetailservice.dto.NewProduct;
import com.nagarro.productdetailservice.dto.ProductUpdateRequest;
import com.nagarro.productdetailservice.entity.ProductDetails;
import com.nagarro.productdetailservice.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductDetailsController {

   @Autowired
   private ProductDetailsService productDetailsService;

    @GetMapping()
    public ResponseEntity<Object> getProduct(){
        List<ProductResponse> products = productDetailsService.fetchProducts();
        if(products.isEmpty()){
            return ResponseEntity.badRequest().body("No products available right now!");
        }
        return ResponseEntity.ok().body(products);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    private ProductResponse saveProduct(@RequestBody NewProduct productDetails){

        return productDetailsService.saveNewProduct(productDetails);
    }

    @GetMapping("/{id}")
    private Object findProductDetailsById(@PathVariable Long id){

        Optional<ProductResponse> productDetails = productDetailsService.fetchProductById(id);

        return productDetails;
    }

    @GetMapping("/price/{id}")
    private double getProductPriceById(@PathVariable Long id){

        return productDetailsService.getProductPrice(id);
    }
    @DeleteMapping("/{id}")
    private boolean deleteProduct(@PathVariable Long id){

        return productDetailsService.deleteProduct(id);
    }

    @PutMapping()
    public ResponseEntity<Object> updateProductDetails(
            @RequestBody ProductUpdateRequest productUpdateRequest){

        ProductResponse response = productDetailsService.updateProductDetails(productUpdateRequest);

        return ResponseEntity.ok().body(response);
    }
}
