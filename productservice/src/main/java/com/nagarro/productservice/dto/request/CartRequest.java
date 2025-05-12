package com.nagarro.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartRequest {

    String userId;
    Long productId;
    Long quantity;
    Long productPrice;

}
