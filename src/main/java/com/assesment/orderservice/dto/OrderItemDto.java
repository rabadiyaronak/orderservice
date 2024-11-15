package com.assesment.orderservice.dto;

import com.assesment.orderservice.model.OrderItem;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class OrderItemDto {

    @NotEmpty
    private String productCode;

    @Positive
    @NotNull
    private Integer quantity;

    @NotNull
    @Positive
    private BigDecimal unitPrice;

    public OrderItem toOrderItemModel() {
        return OrderItem.builder()
                .productCode(productCode)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();

    }
}
