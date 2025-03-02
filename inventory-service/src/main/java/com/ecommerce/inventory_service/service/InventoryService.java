package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.repository.InventoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final RedisTemplate redisTemplate;

    public InventoryService(InventoryRepository inventoryRepository, RedisTemplate redisTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(value = "inventory", key = "#itemId")
    public Integer getStockLevel(String itemId) {
        Integer stock = (Integer) redisTemplate.opsForValue().get("inventory:" + itemId);

        if(stock == null){
            System.out.println("Fetching from Database 🏛️");
            stock = inventoryRepository.findStockByItemId(itemId);
            redisTemplate.opsForValue().set("inventory:" + itemId, stock, Duration.ofMinutes(30));
        }
        return stock;
    }

    @CacheEvict(value = "inventory", key = "#itemId")
    public void updateStock(String itemId, int newStock){
        inventoryRepository.updateStock(itemId, newStock);
        redisTemplate.opsForValue().set("inventory:"+itemId, newStock, Duration.ofMinutes(30));
    }
}
