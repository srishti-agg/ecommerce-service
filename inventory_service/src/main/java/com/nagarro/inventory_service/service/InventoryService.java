package com.nagarro.inventory_service.service;

import com.nagarro.inventory_service.dto.InventoryRequestDTO;
import com.nagarro.inventory_service.entity.Inventory;

import java.util.List;

public interface InventoryService {
    boolean checkProductDetails(Long id, long quantity);

    void saveDetails(InventoryRequestDTO inventoryDetails);

    List<Inventory> getAllProductsCount();

    void deleteProductFromInventory(Long id);

    void updateProductQuantity(InventoryRequestDTO product);
}
