package com.ecommerce.payment_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String userId;
    private double amount;
    private String status;   // "SUCCESS" or "FAILED"
}
