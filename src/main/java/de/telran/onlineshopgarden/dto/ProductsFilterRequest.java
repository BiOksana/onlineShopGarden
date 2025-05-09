package de.telran.onlineshopgarden.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductsFilterRequest {

    @Schema(description = "Category ID to filter products")
    private Long category;

    @Schema(description = "Minimum price of the product")
    private BigDecimal minPrice;

    @Schema(description = "Maximum price of the product")
    private BigDecimal maxPrice;

    @Schema(description = "Filter by products with discount")
    private boolean discount;
}