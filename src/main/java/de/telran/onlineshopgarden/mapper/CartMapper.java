package de.telran.onlineshopgarden.mapper;

import de.telran.onlineshopgarden.dto.CartDto;
import de.telran.onlineshopgarden.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    CartDto entityToDto(Cart entity);
}