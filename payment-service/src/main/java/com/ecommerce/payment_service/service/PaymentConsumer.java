package com.ecommerce.payment_service.service;

import com.ecommerce.payment_service.model.Payment;
import com.ecommerce.payment_service.model.PaymentSuccessEvent;
import com.ecommerce.payment_service.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.ecommerce.dto.OrderEvent;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    @KafkaListener(topics="order_events", groupId = "payment_group")
    public void consumeOrderEvent(String message){
        System.out.println("🔔 Payment Service Received Order Event: " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);

            // Simulate Payment Processing
            boolean isPaymentSuccessful = processPayment(orderEvent.getAmount());

            Payment payment = new Payment(
                    null,
                    orderEvent.getOrderId(),
                    orderEvent.getUserId(),
                    orderEvent.getAmount(),
                    isPaymentSuccessful ? "SUCCESS" : "FAILED"
            );
            paymentRepository.save(payment);

            // Publish Payment Event if successful
            if(isPaymentSuccessful){
                PaymentSuccessEvent paymentEvent = new PaymentSuccessEvent(
                        orderEvent.getOrderId(),
                        orderEvent.getUserId(),
                        "SUCCESS",
                        orderEvent.getItems()
                );
                paymentProducer.sendPaymentSuccessEvent(paymentEvent);
            }
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }

    private boolean processPayment(double amount){
        // Assume payment succeeds if amount < Rs5000
        return amount < 5000;
    }
}
