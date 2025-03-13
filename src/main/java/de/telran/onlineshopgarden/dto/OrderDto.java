package de.telran.onlineshopgarden.dto;

import de.telran.onlineshopgarden.entity.enums.DeliveryMethod;
import de.telran.onlineshopgarden.entity.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer orderId;

    @NotBlank(message = "{validation.order.deliveryAddressNotBlank}")
    @Size(min = 5, max = 255, message = "{validation.order.deliveryAddressSize}")
    private String deliveryAddress;

    @Size(max = 20, message = "{validation.order.contactPhoneSize}")
    private String contactPhone;

    @NotNull(message = "{validation.order.deliveryMethodNotNull}")
    private DeliveryMethod deliveryMethod;

    @NotNull(message = "{validation.order.statusNotNull}")
    private OrderStatus status;

    @NotNull(message = "{validation.order.userIdNotNull}")
    private Integer userId;
}
