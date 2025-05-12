package com.nagarro.orderservice.controller;

import com.nagarro.orderservice.dto.response.Invoice;
import com.nagarro.orderservice.dto.response.OrderConfirmed;
import com.nagarro.orderservice.dto.response.UserOrders;
import com.nagarro.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    private ResponseEntity<Object> createUserOrder(@PathVariable String userId){

        if(userId == null){
            return ResponseEntity.badRequest().body("Please create an account to place order.");
        }
        OrderConfirmed order = orderService.createOrder(userId);
        if(order == null)
            return ResponseEntity.badRequest().body(
                    "Some of the products are out of stock!");
        if(order.orderId().equals("user")){
            return ResponseEntity.notFound().build();
        }
        if(order.totalAmount()==0)
            return ResponseEntity.badRequest().body(order);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    private ResponseEntity<Object> getInvoiceDetails(@PathVariable String orderId){

        List<Invoice> invoice = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok().body(invoice);
    }

    @GetMapping("/confirmed/{userId}")
    private ResponseEntity<Object> getUserOrders(@PathVariable String userId){

        List<UserOrders> userOrdersList = orderService.getUserOrders(userId);

        if(userOrdersList.isEmpty())
            return ResponseEntity.ok().body("Place your first order now!!");
        return ResponseEntity.ok().body(userOrdersList);
    }
}
