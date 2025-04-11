package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.mapper.ProductMapper;
import de.telran.onlineshopgarden.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(
                productRepository,
                productMapper,
                categoryRepository,
                orderItemRepository,
                cartItemRepository,
                favoriteRepository
        );
    }

    @Test
    void getProductOfTheDayMustReturnProductWithMaxDiscount() {
        Product product = new Product();
        product.setProductId(1);
        product.setName("MaxDiscountProduct");
        product.setPrice(BigDecimal.valueOf(100));
        product.setDiscountPrice(BigDecimal.valueOf(60));

        ProductDto dto = new ProductDto();
        dto.setName("MaxDiscountProduct");

        when(productRepository.findProductWithHighestDiscount()).thenReturn(Optional.of(product));
        when(productMapper.entityToDto(product)).thenReturn(dto);

        ProductDto result = service.getProductOfTheDay();

        assertNotNull(result);
        assertEquals("MaxDiscountProduct", result.getName());
    }

    @Test
    void getByIdWhenProductNotFoundMustThrowException() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(99));
    }
}
