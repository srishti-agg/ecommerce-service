package com.nagarro.orderservice.dto.response;

public record UserOrders(String orderId, Long productId, Long quantity, Long orderAmount) {

    static long totalAmount=0;

    public static void setTotalAmount(long totalAmount) {
        UserOrders.totalAmount = totalAmount;
    }
}
