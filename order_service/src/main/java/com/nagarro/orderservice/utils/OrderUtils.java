package com.nagarro.orderservice.utils;

import com.nagarro.orderservice.entity.Orders;

import java.util.UUID;

public class OrderUtils {

    public static Orders createOrderEntity(String orderId, String userId, Long productId, Long amount,
                                           Long quantity){

        return Orders.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderId(orderId)
                .userId(userId)
                .productId(productId)
                .orderAmount(amount)
                .quantity(quantity)
                .build();
    }
}
