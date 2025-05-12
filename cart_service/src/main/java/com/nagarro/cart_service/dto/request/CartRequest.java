package com.nagarro.cart_service.dto.request;

public record CartRequest(Long productId, String userId, Long quantity) {
}
