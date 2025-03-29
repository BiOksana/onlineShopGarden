package de.telran.onlineshopgarden.mapper;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "image", source = "imageUrl")
    ProductDto entityToDto(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "imageUrl", source = "image")
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product createOrUpdateDtoToEntity(ProductDto dto);

    List<ProductDto> entityListToDtoList(List<Product> productList);

    default Page<ProductDto> toDtoPage(Page<Product> products) {
        return products.map(this::entityToDto);
    }
}