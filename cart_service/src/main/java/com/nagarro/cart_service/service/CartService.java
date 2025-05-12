package com.nagarro.cart_service.service;

import com.nagarro.cart_service.dto.request.CartRequest;
import com.nagarro.cart_service.dto.response.UpdatedCart;
import com.nagarro.cart_service.dto.response.UserCart;
import com.nagarro.cart_service.entity.Cart;

import java.util.List;

public interface CartService {

    UpdatedCart saveProductInCart(CartRequest cart);

    List<UserCart> getUserCartDetails(String userId);

    List<Cart> getAllCartDetails();

    void deleteUserCart(String userId);
}
