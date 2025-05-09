package de.telran.onlineshopgarden.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemFullDto {

    private Long id;

    private Long productId;

    private int quantity;

    private BigDecimal priceAtPurchase;
}