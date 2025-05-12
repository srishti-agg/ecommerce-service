package com.nagarro.cart_service.service.impl;

import com.nagarro.cart_service.dto.request.CartRequest;
import com.nagarro.cart_service.dto.response.UpdatedCart;
import com.nagarro.cart_service.dto.response.UserCart;
import com.nagarro.cart_service.entity.Cart;
import com.nagarro.cart_service.repository.CartRepository;
import com.nagarro.cart_service.service.CartService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.nagarro.cart_service.utils.CartUtils.createCartEntity;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackSaveProductInCart" )
    public UpdatedCart saveProductInCart(CartRequest cart) {

        Cart product = cartRepository.findByUserIdAndProductId(cart.userId(), cart.productId());
        if(product==null){
            Cart newEntry = createCartEntity(cart.userId(),cart.productId(),cart.quantity());
            cartRepository.save(newEntry);
            return new UpdatedCart(cart.productId(),cart.quantity());
        }
        product.setQuantity(product.getQuantity()+cart.quantity());
        cartRepository.save(product);
        return new UpdatedCart(product.getProductId(), product.getQuantity());
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackGetUserCartDetails" )
    public List<UserCart> getUserCartDetails(String userId) {

        List<Cart> cartList = cartRepository.findByUserId(userId);
        if(cartList.isEmpty()){
            return null;
        }

        return cartList.stream().map(item->
                new UserCart(item.getProductId(), item.getQuantity())).toList();
    }

    @Override
    public List<Cart> getAllCartDetails() {
        return cartRepository.findAll();
    }

    @Override
    public void deleteUserCart(String userId) {

        List<Cart> cartList = cartRepository.findByUserId(userId);

        for(Cart cart : cartList) {
            cartRepository.deleteById(cart.getCartId());
        }
    }

    public UpdatedCart fallbackSaveProductInCart(Throwable throwable){

        return new UpdatedCart(0L,0L);
    }

    private List<UserCart> fallbackGetUserCartDetails(Throwable throwable){

        return new ArrayList<>();
    }
}
