package de.telran.onlineshopgarden.mapper;

import de.telran.onlineshopgarden.dto.OrderItemDto;
import de.telran.onlineshopgarden.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productId", ignore = true)
    OrderItemDto entityToDto(OrderItem orderItem);

    List<OrderItemDto> entityListToDto(List<OrderItem> orderItems);
}