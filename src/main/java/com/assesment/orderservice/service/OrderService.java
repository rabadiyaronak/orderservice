package com.assesment.orderservice.service;

import com.assesment.orderservice.dto.OrderCreationRequest;
import com.assesment.orderservice.dto.OrderCreationResponse;

public interface OrderService {
    OrderCreationResponse createOrder(OrderCreationRequest request);
}
