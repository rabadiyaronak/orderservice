package com.assesment.orderservice.controller;

import com.assesment.orderservice.dto.OrderCreationRequest;
import com.assesment.orderservice.dto.OrderCreationResponse;
import com.assesment.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderCreationResponse> create(@Valid  @RequestBody OrderCreationRequest request) {
        OrderCreationResponse response =  orderService.createOrder(request);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(201));
    }
}
