package com.nagarro.productservice.client;

import com.nagarro.productservice.dto.request.InventoryDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/inventory")
    ResponseEntity<Object> saveInventoryDetails(
            InventoryDetails inventoryDetails);
}
