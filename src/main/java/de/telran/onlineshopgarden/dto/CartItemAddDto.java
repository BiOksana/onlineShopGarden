package de.telran.onlineshopgarden.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemAddDto {

    private Long productId;

    @NotNull(message = "{validation.cartItem.quantityNotBlank}")
    @Min(value = 1, message = "{validation.cartItem.quantityMin}")
    private Integer quantity;
}