package de.telran.onlineshopgarden.mapper;

import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto entityToDto(Order order);

    List<OrderDto> entityListToDto(List<Order> entities);
}