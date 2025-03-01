package com.ecommerce.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String orderId;
    private String userId;
    private double amount;
    private String status;  // NEW, PROCESSING, COMPLETED

    private List<OrderItem> items;  // ✅ Multiple items per order
}
