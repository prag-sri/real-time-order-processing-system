package com.ecommerce.inventory_service.repository;

import com.ecommerce.inventory_service.model.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByItemId(String itemId);

    @Query("SELECT i.stock FROM Inventory i WHERE i.itemId = :itemId")
    Integer findStockByItemId(@Param("itemId") String itemId);

    @Modifying
    @Transactional
    @Query("UPDATE Inventory i SET i.stock = :newStock WHERE i.itemId = :itemId")
    void updateStock(@Param("itemId") String itemId, @Param("newStock") int newStock);
}
