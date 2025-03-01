package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.ecommerce.dto.PaymentSuccessEvent;
import com.ecommerce.dto.OrderItem;

import java.util.List;

@Service
public class InventoryConsumer {

    private final InventoryRepository inventoryRepository;

    public InventoryConsumer(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    @KafkaListener(topics = "payment_events", groupId = "inventory_group")
    public void handlePaymentEvent(String message){

        System.out.println("📦 Inventory Service Received Order Event: " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            PaymentSuccessEvent paymentSuccessEvent = objectMapper.readValue(message, PaymentSuccessEvent.class);

            //  paymentSuccessEvent.setStatus("SUCCESS");  -> testing
            if("SUCCESS".equals(paymentSuccessEvent.getStatus())) {
                List<OrderItem> items = paymentSuccessEvent.getItems();

                for(OrderItem item: items){
                    String itemId = item.getItemId();
                    int quantity = item.getQuantity();

                    // Fetch inventory from DB
                    Inventory inventory = inventoryRepository.findByItemId(itemId).orElse(null);
                    if(inventory!=null && inventory.getStock() >= quantity){
                        // Deduct stock
                        inventory.setStock(inventory.getStock() - quantity);
                        inventoryRepository.save(inventory);
                        System.out.println("✅ Stock updated for item: " + itemId + ". Remaining: " + inventory.getStock());
                    }
                    else{
                        System.out.println("❌ Out of stock for item: " + itemId);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Unexpected Error in Inventory Consumer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
