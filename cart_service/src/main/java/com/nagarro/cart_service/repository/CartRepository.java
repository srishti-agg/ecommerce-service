package com.nagarro.cart_service.repository;

import com.nagarro.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query(value = "SELECT u FROM Cart u WHERE u.userId = :userId AND u.productId = :productId")
    Cart findByUserIdAndProductId(String userId, Long productId);

    List<Cart> findByUserId(String userId);

}
