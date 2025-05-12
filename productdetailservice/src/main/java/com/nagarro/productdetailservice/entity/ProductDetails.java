package com.nagarro.productdetailservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductDetails {

    @Id()
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(updatable = false, nullable = false)
    long productId;

    @Column(nullable = false)
    double price;

    @Column(nullable = false)
    String size;

    @Column(nullable = false)
    String productName;

    @Column(nullable = false)
    String productType;

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    String color;
}
