package com.nagarro.cart_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Cart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long cartId;

    long productId;

    String userId;

    long quantity;

}
