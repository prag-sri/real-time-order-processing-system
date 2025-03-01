package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderEvent;
import com.ecommerce.order_service.model.OrderItem;
import com.ecommerce.order_service.service.OrderProducer;
import com.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;
    private final OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody Order orderRequest){
        Order order = new Order(
                null,
                orderRequest.getUserId(),
                orderRequest.getAmount(),
                "NEW",
                orderRequest.getItems()
        );

        // Set parent reference for each OrderItemEntity
        order.getItems().forEach(item -> item.setOrder(order));

        orderService.saveOrder(order);

        // Convert List<OrderItemEntity> to List<OrderItem>
        List<OrderItem> orderItems = order.getItems().stream()
                .map(itemEntity -> new OrderItem(itemEntity.getItemId(), itemEntity.getQuantity()))
                .toList();

        OrderEvent orderEvent = new OrderEvent(
                order.getId().toString(),
                orderRequest.getUserId(),
                orderRequest.getAmount(),
                "NEW",
                orderItems);

        orderProducer.sendOrderEvent(orderEvent);
        return "✅ Order Created & Event Published: "+order.getId().toString();
    }
}
