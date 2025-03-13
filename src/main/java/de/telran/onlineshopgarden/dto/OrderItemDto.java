package de.telran.onlineshopgarden.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    @NotNull(message = "{validation.order.productIdNotBlank}")
    private String productId;

    @NotNull(message = "{validation.order.quantityNotBlank}")
    @Min(value = 1, message = "{validation.order.quantityMin}")
    private Integer quantity;
}
