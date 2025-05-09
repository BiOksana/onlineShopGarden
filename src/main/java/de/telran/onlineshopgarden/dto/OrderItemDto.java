package de.telran.onlineshopgarden.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long productId;

    @Min(value = 1, message = "{validation.orderItem.quantityMin}")
    private int quantity;
}