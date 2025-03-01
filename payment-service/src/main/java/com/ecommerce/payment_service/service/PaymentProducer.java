package com.ecommerce.payment_service.service;

import com.ecommerce.payment_service.model.PaymentSuccessEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendPaymentSuccessEvent(PaymentSuccessEvent paymentSuccessEvent){
        try{
            String message = objectMapper.writeValueAsString(paymentSuccessEvent);
            kafkaTemplate.send("payment_events", message);
            System.out.println("💰 Payment Success Event Sent: " + message);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
