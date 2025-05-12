package com.nagarro.inventory_service.controller;

import com.nagarro.inventory_service.client.UserClient;
import com.nagarro.inventory_service.dto.InventoryRequestDTO;
import com.nagarro.inventory_service.entity.Inventory;
import com.nagarro.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserClient userClient;

    @PostMapping("/check")
    public boolean checkProductQuantity(@RequestBody InventoryRequestDTO inventoryRequestDTO){

        return inventoryService.checkProductDetails(
                inventoryRequestDTO.productId(), inventoryRequestDTO.product_count());
    }

    @PostMapping()
    public ResponseEntity<Object> saveInventoryDetails(
            @RequestBody InventoryRequestDTO inventoryDetails){

        inventoryService.saveDetails(inventoryDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public List<Inventory> getAllProductsCount(){

        return inventoryService.getAllProductsCount();
    }

    @DeleteMapping("/{id}/{username}")
    public ResponseEntity<Object> deleteProductFromInventory(@PathVariable Long id,
                                                             @PathVariable String username){

        if(!userClient.isUserAdmin(username)){
            return ResponseEntity.badRequest().body("You need admin rights to perform this action!");
        }
        inventoryService.deleteProductFromInventory(id);
        return ResponseEntity.ok().body("Deleted product details from inventory.");
    }

    @PutMapping()
    public ResponseEntity<Object> updateInventory(
            @RequestBody InventoryRequestDTO inventoryRequestDTO){

        inventoryService.updateProductQuantity(inventoryRequestDTO);
        return ResponseEntity.ok().build();
    }
}
