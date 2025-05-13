package de.telran.onlineshopgarden.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductDto {

    private Long id;

    @NotBlank(message = "{validation.product.name}")
    @Pattern(regexp = "[A-Za-z ]{1,45}", message = "{validation.product.name}")
    private String name;

    @Length(max = 1500, message ="{validation.product.description}")
    private String description;

    @NotNull(message = "{validation.product.price}")
    @DecimalMin(value = "0.01", message = "{validation.product.price}")
    private BigDecimal price;

    @NotNull(message = "{validation.product.categoryId}")
    private Long categoryId;

    @NotNull(message = "{validation.product.imageNotNull}")
    @Pattern(regexp = "^https://[^\\s/$.?#][^\\s]{0,140}\\.(jpg|jpeg|png)(\\?.*)?$", message = "{validation.product.imageValidation}")
    private String image;

    @DecimalMin(value = "0.01", message = "{validation.product.discountPrice}")
    private BigDecimal discountPrice;
}