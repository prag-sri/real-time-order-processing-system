package com.ecommerce.payment_service.model;

import com.ecommerce.dto.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccessEvent {
    private String orderId;
    private String userId;
    private String status;
    private List<OrderItem> items; // Include items for inventory
}
