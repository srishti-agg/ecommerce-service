package com.nagarro.inventory_service.service.impl;

import com.nagarro.inventory_service.dto.InventoryRequestDTO;
import com.nagarro.inventory_service.entity.Inventory;
import com.nagarro.inventory_service.repository.InventoryRepository;
import com.nagarro.inventory_service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nagarro.inventory_service.utils.InventoryBuilder.createInventoryEntity;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public boolean checkProductDetails(Long id, long quantity) {

        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.filter(value -> value.getProduct_count() >= quantity).isPresent();

    }

    @Override
    public void saveDetails(InventoryRequestDTO inventoryDetails) {

        log.info("Save new product in inventory service impl starts.");
        Inventory inventory = createInventoryEntity(inventoryDetails.productId(),
                inventoryDetails.product_count());
        inventoryRepository.save(inventory);
        log.info("Save new product in inventory service impl ends.");
    }

    @Override
    public List<Inventory> getAllProductsCount() {
        return inventoryRepository.findAll();
    }

    @Override
    public void deleteProductFromInventory(Long id) {

        log.info("Delete product from inventory details starts");
        Optional<Inventory> productInventory = inventoryRepository.findById(id);

        if(productInventory.isEmpty()){
            log.warn("No such product exists in inventory!");
            return;
        }

        inventoryRepository.deleteById(id);
        log.info("Delete product from inventory details ends");
    }

    @Override
    public void updateProductQuantity(InventoryRequestDTO productDetails) {

        Inventory inventory = inventoryRepository.findById(
                productDetails.productId()).orElse(new Inventory(-1,-1));

        if (inventory.getProductId()==-1){
            return;
        }

        inventory.setProduct_count(inventory.getProduct_count()-productDetails.product_count());
        inventoryRepository.save(inventory);

    }


}
