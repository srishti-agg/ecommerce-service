package com.nagarro.productservice.dto.request;

public record ProductUpdateRequest(Long productId, double price, String size, String productName,
                      String color, String gender, String productType) {
}
