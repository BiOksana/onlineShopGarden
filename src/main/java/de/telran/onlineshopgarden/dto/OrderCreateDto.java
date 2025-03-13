package de.telran.onlineshopgarden.dto;

import de.telran.onlineshopgarden.entity.enums.DeliveryMethod;
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

    private List<OrderItemDto> items;
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
}
