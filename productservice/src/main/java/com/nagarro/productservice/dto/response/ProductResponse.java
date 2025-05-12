package com.nagarro.productservice.dto.response;


public record ProductResponse(Long id, double price, String size, String productName,
                              String color, String gender, String productType) {

}
