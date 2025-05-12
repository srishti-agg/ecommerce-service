package com.nagarro.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orders {


    @Id
    String orderNumber;

    String orderId;
    long orderAmount;
    long productId;
    long quantity;
    String userId;
}
