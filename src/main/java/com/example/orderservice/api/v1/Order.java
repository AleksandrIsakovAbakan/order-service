package com.example.orderservice.api.v1;

import lombok.Data;

@Data
public class Order {

    private String product;

    private Integer quantity;
}
