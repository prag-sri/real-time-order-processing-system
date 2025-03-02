package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("{itemId}/stock")
    public ResponseEntity<String> getStockLevel(@PathVariable String itemId) {
        Integer stock = inventoryService.getStockLevel(itemId);
        return new ResponseEntity<>("Current Stock: " + (stock != null ? stock : 0), HttpStatus.OK);
    }

    @PutMapping("{itemId}/stock")
    public ResponseEntity<String> updateStock(@PathVariable String itemId, @RequestParam int newStock) {
        inventoryService.updateStock(itemId, newStock);
        return new ResponseEntity<>("Stock updated successfully!", HttpStatus.ACCEPTED);
    }
}
