package com.nagarro.cart_service.controller;

import com.nagarro.cart_service.dto.request.CartRequest;
import com.nagarro.cart_service.dto.response.UpdatedCart;
import com.nagarro.cart_service.dto.response.UserCart;
import com.nagarro.cart_service.entity.Cart;
import com.nagarro.cart_service.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cart")
@RestController
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private CartService cartService;

    @PostMapping()
    private ResponseEntity<Object> saveProductInCart(@RequestBody CartRequest cart){

        if(cart.userId().isEmpty()){
            log.info("User id is null");
            return ResponseEntity.badRequest().body(
                    "Please login to add items to your cart!");
        }
        UpdatedCart userCart = cartService.saveProductInCart(cart);
        log.info("Items added successfully to cart. {}", userCart.toString());
        return ResponseEntity.ok().body(userCart);
    }

    @GetMapping("/{userId}")
    private List<UserCart> getUserCartDetails(@PathVariable String userId){

        return cartService.getUserCartDetails(userId);
    }

    @GetMapping()
    private List<Cart> getAllCart(){

        return cartService.getAllCartDetails();
    }

    @DeleteMapping("/{userId}")
    private ResponseEntity<Object> emptyUserCart(@PathVariable String userId){

        cartService.deleteUserCart(userId);
        log.info("User cart is empty!! {}", userId);
        return ResponseEntity.ok().build();
    }

}
