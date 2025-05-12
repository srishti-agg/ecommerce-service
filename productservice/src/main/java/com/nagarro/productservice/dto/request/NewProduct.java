package com.nagarro.productservice.dto.request;

public record NewProduct(double price, String size, String productName,
                         String color, String gender, String productType, Long product_count){
}
