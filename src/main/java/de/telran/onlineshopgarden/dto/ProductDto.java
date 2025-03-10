package de.telran.onlineshopgarden.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private Integer productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer categoryId;

    private String imageUrl;

    private BigDecimal discountPrice;
}
