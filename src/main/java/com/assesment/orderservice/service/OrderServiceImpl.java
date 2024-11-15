package com.assesment.orderservice.service;

import com.assesment.orderservice.dto.OrderCreationRequest;
import com.assesment.orderservice.dto.OrderCreationResponse;
import com.assesment.orderservice.dto.OrderItemDto;
import com.assesment.orderservice.model.Order;
import com.assesment.orderservice.model.OrderItem;
import com.assesment.orderservice.model.OrderStatus;
import com.assesment.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderCreationResponse createOrder(OrderCreationRequest request) {
        Order order = request.toOrderModel();
        order.getItems().forEach(orderItem -> orderItem.setOrder(order));
        BigDecimal orderTotal = getOrderTotal(order);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(Instant.now());
        order.setTotalAmount(orderTotal);
        order.setOrderNumber(UUID.randomUUID().toString());

        Order persistedOrder = orderRepository.save(order);

        return convertToOrderCreationResponse(persistedOrder);
    }

    private OrderCreationResponse convertToOrderCreationResponse(Order persistedOrder) {
        List<OrderItemDto> items = persistedOrder.getItems().stream().map(this::convertToOrderItemDto)
                .collect(Collectors.toList());
        return OrderCreationResponse.builder()
                .orderStatus(persistedOrder.getStatus())
                .orderNumber(persistedOrder.getOrderNumber())
                .totalAmount(persistedOrder.getTotalAmount())
                .items(items)
                .build();
    }

    private OrderItemDto convertToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productCode(orderItem.getProductCode())
                .unitPrice(orderItem.getUnitPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    private static BigDecimal getOrderTotal(Order order) {
        BigDecimal orderTotal = order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return orderTotal;
    }
}
