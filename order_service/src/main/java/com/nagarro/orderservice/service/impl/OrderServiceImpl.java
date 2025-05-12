package com.nagarro.orderservice.service.impl;

import com.nagarro.orderservice.client.CartClient;
import com.nagarro.orderservice.client.InventoryClient;
import com.nagarro.orderservice.client.ProductDetailsClient;
import com.nagarro.orderservice.dto.request.InventoryRequestDTO;
import com.nagarro.orderservice.dto.request.UserCart;
import com.nagarro.orderservice.dto.response.Invoice;
import com.nagarro.orderservice.dto.response.OrderConfirmed;
import com.nagarro.orderservice.dto.response.UserOrders;
import com.nagarro.orderservice.entity.Orders;
import com.nagarro.orderservice.respository.OrderRepository;
import com.nagarro.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.nagarro.orderservice.utils.OrderUtils.createOrderEntity;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String TOPIC = "ecommerceTopic";

//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate;
//
//
//    @Override
//    public void sendNotification(String data) {
//        kafkaTemplate.send(TOPIC,"1",data);
//    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private CartClient cartClient;

    @Autowired
    private ProductDetailsClient productDetailsClient;

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackResponse" )
    public OrderConfirmed createOrder(String userId) {

        List<UserCart> userCartItems = cartClient.getUserCartDetails(userId);

        if(userCartItems == null){
            return new OrderConfirmed("user",0L);
        }
        for (UserCart item : userCartItems) {
            InventoryRequestDTO inventoryRequestDTO = new InventoryRequestDTO(
                    item.productId(), item.quantity());
            if (!inventoryClient.checkProductQuantity(inventoryRequestDTO)){
//                sendNotification("Quantity of product id: "+item.productId()+" " +
//                        "is less than the required.");
                return null;
            }
        }

        long totalAmount=0L;
        String orderId=UUID.randomUUID().toString();

        for (UserCart item : userCartItems) {
            double price= productDetailsClient.getProductPriceById(item.productId());//TODO add product details service to get product price
            long amount = (long) (item.quantity() * price);
            Orders order = createOrderEntity(orderId,
                    userId, item.productId(), amount
                    , item.quantity());
            orderRepository.save(order);
            totalAmount += amount;
            inventoryClient.updateInventory(new InventoryRequestDTO
                    (item.productId(), item.quantity()));
        }
        cartClient.emptyUserCart(userId);
//        sendNotification("Order is placed successfully with order id: "+
//                orderId+" and total amount: "+totalAmount);
        return new OrderConfirmed(orderId,totalAmount);
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackGetOrderDetails" )
    public List<Invoice> getOrderDetails(String orderId) {

        List<Orders> ordersList = orderRepository.findAllByOrderId(orderId);
        return ordersList.stream().map(entry->
                new Invoice(entry.getProductId(),
                        entry.getQuantity(),entry.getOrderAmount())).toList();
    }

    @Override
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackUserOrders" )
    public List<UserOrders> getUserOrders(String userId) {

        List<Orders> orders = orderRepository.findAllByUserId(userId);

        return orders.stream().map(
                entry-> new UserOrders(entry.getOrderId(), entry.getProductId(),
                        entry.getQuantity(), entry.getOrderAmount())
        ).toList();
    }

    public OrderConfirmed fallbackResponse(Throwable throwable){

        return new OrderConfirmed("Could not be generated!",0L);
    }

    private List<Invoice> fallbackGetOrderDetails(Throwable throwable){

        return new ArrayList<>();
    }

    List<UserOrders> fallbackUserOrders(Throwable throwable){
        return new ArrayList<>();
    }
}
