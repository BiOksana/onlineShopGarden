package de.telran.onlineshopgarden.dto;

import de.telran.onlineshopgarden.entity.enums.DeliveryMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {

    @NotNull(message = "{validation.order.itemsNotNull}")
    @Size(min = 1, message = "{validation.order.itemsNotNull}")
    private List<OrderItemDto> items;

    @NotBlank(message = "{validation.order.deliveryAddressNotBlank}")
    @Size(min = 5, max = 255, message = "{validation.order.deliveryAddressSize}")
    private String deliveryAddress;

    @NotNull(message = "{validation.order.deliveryMethodNotNull}")
    private DeliveryMethod deliveryMethod;
}
