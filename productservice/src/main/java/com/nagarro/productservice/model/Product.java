package com.nagarro.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    long productId;
    double price;
    String size;
    String productName;
    String color;
    String gender;
    String productType;
//    long product_count;
}
