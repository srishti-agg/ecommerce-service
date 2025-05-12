package com.nagarro.productdetailservice.utils;

import com.nagarro.productdetailservice.entity.ProductDetails;

public class ProductUtils {

    public static ProductDetails createProductEntity(double price, String size, String productName,
                                                   String color, String gender, String productType){

        return ProductDetails.builder()
                .productId(0L)
                .productName(productName)
                .productType(productType)
                .price(price)
                .color(color)
                .gender(gender)
                .size(size)
                .build();
    }
}
