package de.telran.onlineshopgarden.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String productId;

    @NotBlank(message = "{validation.product.name}")
    @Pattern(regexp = "[A-Za-z ]{1,45}", message = "{validation.product.name}")
    private String name;

    @Length(max = 1500, message ="{validation.product.description}")
    private String description;

    @NotNull(message = "{validation.product.price}")
    @DecimalMin(value = "0.01", message = "{validation.product.price}")
    private BigDecimal price;

    @Min(value = 0, message = "{validation.product.discountPercentage}")
    @Max(value = 100, message = "{validation.product.discountPercentage}")
    private Integer discountPercentage;

    @DecimalMin(value = "0.0", message = "{validation.product.discountPrice}")
    private BigDecimal discountPrice;

    @NotNull(message = "{validation.product.categoryId}")
    private Integer categoryId;

    @NotNull(message = "{validation.product.imageNotNull}")
    @Pattern(regexp = "^https://[^\\s/$.?#][^\\s]{0,140}\\.(jpg|jpeg|png)(\\?.*)?$", message = "{validation.product.imageValidation}")
    private String image;


}