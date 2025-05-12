package com.nagarro.orderservice.respository;

import com.nagarro.orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Orders, String> {
    List<Orders> findAllByOrderId(String orderId);

    List<Orders> findAllByUserId(String userId);
}
