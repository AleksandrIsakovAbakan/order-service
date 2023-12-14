package com.example.orderservice.controller;

import com.example.orderservice.api.v1.Order;
import com.example.orderservice.model.OrderEvent;
import com.example.orderservice.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaMessageService kafkaMessageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Order message){
        kafkaMessageService.sendMessage(message);
        return ResponseEntity.ok("Message send to kafka");
    }

    @GetMapping("/{product}")
    public ResponseEntity<OrderEvent> getById(@PathVariable String product){
        return ResponseEntity.ok(kafkaMessageService.getById(product).orElseThrow());
    }
}
