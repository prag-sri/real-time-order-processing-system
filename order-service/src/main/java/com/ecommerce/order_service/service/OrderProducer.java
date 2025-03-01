package com.ecommerce.order_service.service;

import com.ecommerce.order_service.model.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent){
        kafkaTemplate.send("order_events", orderEvent);
        System.out.println("✅ Order Event Sent: "+orderEvent);
    }
}
