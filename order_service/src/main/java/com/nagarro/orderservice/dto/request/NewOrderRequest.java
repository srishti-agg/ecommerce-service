package com.nagarro.orderservice.dto.request;

public record NewOrderRequest(Long productId, String userId, Long quantity, Long productPrice) {
}
