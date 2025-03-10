package de.telran.onlineshopgarden.mapper;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto entityToDto(Product product);

    List<ProductDto> entityListToDtoList(List<Product> productList);
}
