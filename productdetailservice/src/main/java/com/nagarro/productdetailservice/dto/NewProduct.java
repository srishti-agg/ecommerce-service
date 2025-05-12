package com.nagarro.productdetailservice.dto;

public record NewProduct(double price, String size, String productName,
       String color, String gender, String productType, long quantity) {
}
