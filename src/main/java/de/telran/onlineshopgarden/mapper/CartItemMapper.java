package de.telran.onlineshopgarden.mapper;

import de.telran.onlineshopgarden.dto.CartItemAddDto;
import de.telran.onlineshopgarden.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem dtoToEntity(CartItemAddDto dto);
}