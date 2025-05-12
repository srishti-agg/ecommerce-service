package com.nagarro.orderservice.service;

import com.nagarro.orderservice.dto.response.Invoice;
import com.nagarro.orderservice.dto.response.OrderConfirmed;
import com.nagarro.orderservice.dto.response.UserOrders;

import java.util.List;

public interface OrderService {

//    void sendNotification(String data);

    OrderConfirmed createOrder(String userId);

    List<Invoice> getOrderDetails(String orderId);

    List<UserOrders> getUserOrders(String userId);
}
