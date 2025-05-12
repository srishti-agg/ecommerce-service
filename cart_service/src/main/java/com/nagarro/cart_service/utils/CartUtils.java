package com.nagarro.cart_service.utils;

import com.nagarro.cart_service.entity.Cart;

public class CartUtils {

    public static Cart createCartEntity(String userId, long productId, long quantity){

        return Cart.builder()
                .cartId(0L)
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
