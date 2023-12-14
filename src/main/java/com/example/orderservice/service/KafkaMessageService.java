package com.example.orderservice.service;

import com.example.orderservice.api.v1.Order;
import com.example.orderservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageService {


    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private final List<OrderEvent> messages = new ArrayList<>();

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;

    public void add(OrderEvent message){
        messages.add(message);
    }

    public Optional<OrderEvent> getById(String product){
        return messages.stream().filter(p -> p.getProduct().equals(product)).findFirst();
    }

    public void sendMessage(Order message){
        log.info("order-service " + message);
        kafkaTemplate.send(topicName, orderToOrderEvent(message));
    }

    public OrderEvent orderToOrderEvent(Order message){
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setProduct(message.getProduct());
        orderEvent.setQuantity(message.getQuantity());
        return orderEvent;
    }
}
