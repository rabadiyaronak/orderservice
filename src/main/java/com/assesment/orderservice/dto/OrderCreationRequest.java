package com.assesment.orderservice.dto;

import com.assesment.orderservice.model.Order;
import com.assesment.orderservice.model.OrderItem;
import com.assesment.orderservice.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCreationRequest {

    @NotEmpty(message = "Order request must have at least one item")
    @JsonProperty("items")
    private List<@NotNull OrderItemDto> items ;

    public Order toOrderModel() {
        List<OrderItem> orderItems = items.stream().map(OrderItemDto::toOrderItemModel)
                .collect(Collectors.toList());

        return Order.builder()
                .items(orderItems)
                .build();
    }
}
