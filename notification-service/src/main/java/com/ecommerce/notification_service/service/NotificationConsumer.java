package com.ecommerce.notification_service.service;

import com.ecommerce.dto.PaymentSuccessEvent;
import com.ecommerce.notification_service.model.Notification;
import com.ecommerce.notification_service.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    public NotificationConsumer(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "payment_events", groupId = "notification_group")
    public void consumeOrderEvent(String message){
        System.out.println("📩 Notification Service Received Order Event: " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            PaymentSuccessEvent paymentSuccessEvent = objectMapper.readValue(message, PaymentSuccessEvent.class);

            //  paymentSuccessEvent.setStatus("SUCCESS");  -> testing
            if("SUCCESS".equals(paymentSuccessEvent.getStatus())) {
                // Save notification to DB
                Notification notification = new Notification(
                        null,
                        paymentSuccessEvent.getUserId(),
                        paymentSuccessEvent.getOrderId(),
                        "Order placed successfully!",
                        LocalDateTime.now()
                );
                notificationRepository.save(notification);

                System.out.println("📧 Email/SMS sent to User: " + paymentSuccessEvent.getUserId());
            }
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
