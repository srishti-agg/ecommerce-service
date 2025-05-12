package com.nagarro.productdetailservice.dto;

public record ProductResponse(Long id,double price, String size, String productName,
                              String color, String gender, String productType) {
}
