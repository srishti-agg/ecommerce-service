package com.nagarro.inventory_service.utils;

import com.nagarro.inventory_service.entity.Inventory;

public class InventoryBuilder {

    public static Inventory createInventoryEntity(Long productId, Long quantity){

        return Inventory.builder()
                .productId(productId)
                .product_count(quantity)
                .build();
    }
}
