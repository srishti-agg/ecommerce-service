package com.nagarro.orderservice.client;

import com.nagarro.orderservice.dto.request.InventoryRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/inventory/check")
    boolean checkProductQuantity(InventoryRequestDTO inventoryRequestDTO);

    @PutMapping("/inventory")
    ResponseEntity<Object> updateInventory(
            InventoryRequestDTO inventoryRequestDTO);
}
