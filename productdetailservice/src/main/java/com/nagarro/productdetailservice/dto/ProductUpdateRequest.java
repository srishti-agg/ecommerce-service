package com.nagarro.productdetailservice.dto;

public record ProductUpdateRequest(Long productId, double price, String size,
                                   String productName,
                                   String color, String gender,String productType) {
}
