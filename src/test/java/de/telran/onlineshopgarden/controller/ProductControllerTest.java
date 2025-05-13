package de.telran.onlineshopgarden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.onlineshopgarden.config.SecurityConfig;
import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.dto.ProductsFilterRequest;
import de.telran.onlineshopgarden.exception.ResourceNotFoundException;
import de.telran.onlineshopgarden.security.JwtProvider;
import de.telran.onlineshopgarden.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
@Import(SecurityConfig.class)
class ProductControllerTest {

    @MockitoBean
    private ProductService service;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    private List<ProductDto> products;
    private ProductDto tulipProduct;
    private ProductDto charcoalProduct;
    private ProductDto shovelProduct;
    private final ArgumentCaptor<Pageable> pageCaptor = ArgumentCaptor.forClass(Pageable.class);
    private final ArgumentCaptor<ProductsFilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(ProductsFilterRequest.class);

    @BeforeEach
    void setUp() {
        tulipProduct = new ProductDto(1L, "Tulip", "Description1", BigDecimal.valueOf(10), 1L, "https://test.jpeg", null);
        charcoalProduct = new ProductDto(2L, "Charcoal", "Description2", BigDecimal.valueOf(20), 3L, "https://test2.jpeg", null);
        shovelProduct = new ProductDto(3L, "Shovel", "Description3", BigDecimal.valueOf(30), 5L, "https://test3.jpeg", BigDecimal.valueOf(19.50));
        products = List.of(
                tulipProduct,
                charcoalProduct,
                shovelProduct
        );
    }

    @Test
    void getProductsDefaultPaginationNoFilters() throws Exception {
        Page<ProductDto> page = new PageImpl<>(products, PageRequest.of(0, 5), products.size());
        when(service.getFiltered(any(ProductsFilterRequest.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(page)));

        verify(service).getFiltered(filterRequestArgumentCaptor.capture(), pageCaptor.capture());
        Pageable pageable = pageCaptor.getValue();
        ProductsFilterRequest productsFilterRequest = filterRequestArgumentCaptor.getValue();
        assertEquals(5, pageable.getPageSize());
        assertEquals(0, pageable.getPageNumber());
        assertTrue(pageable.getSort().getOrderFor("name").isAscending());

        assertNull(productsFilterRequest.getCategory());
        assertNull(productsFilterRequest.getMinPrice());
        assertNull(productsFilterRequest.getMaxPrice());
        assertFalse(productsFilterRequest.isDiscount());
    }

    @Test
    void getProductsCustomPaginationAndSorting() throws Exception {
        Page<ProductDto> page = new PageImpl<>(products, PageRequest.of(1, 2, Sort.by("price").descending()), products.size());
        when(service.getFiltered(any(ProductsFilterRequest.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/products")
                        .param("page", "1")
                        .param("size", "2")
                        .param("sort", "price,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(page)));

        verify(service).getFiltered(filterRequestArgumentCaptor.capture(), pageCaptor.capture());

        Pageable pageable = pageCaptor.getValue();
        assertEquals(2, pageable.getPageSize());
        assertEquals(1, pageable.getPageNumber());
        assertTrue(pageable.getSort().getOrderFor("price").isDescending());

        ProductsFilterRequest productsFilterRequest = filterRequestArgumentCaptor.getValue();
        assertNull(productsFilterRequest.getCategory());
        assertNull(productsFilterRequest.getMinPrice());
        assertNull(productsFilterRequest.getMaxPrice());
        assertFalse(productsFilterRequest.isDiscount());
    }

    @Test
    void getProductsFilterByCategoryAndDiscountAndByPriceRange() throws Exception {
        Page<ProductDto> page = new PageImpl<>(products, PageRequest.of(0, 5), products.size());
        when(service.getFiltered(any(ProductsFilterRequest.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/products")
                        .param("category", "1")
                        .param("discount", "true")
                        .param("minPrice", "10.0")
                        .param("maxPrice", "30.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(page)));

        verify(service).getFiltered(filterRequestArgumentCaptor.capture(), pageCaptor.capture());
        Pageable pageable = pageCaptor.getValue();
        assertEquals(5, pageable.getPageSize());
        assertEquals(0, pageable.getPageNumber());
        assertTrue(pageable.getSort().getOrderFor("name").isAscending());

        ProductsFilterRequest productsFilterRequest = filterRequestArgumentCaptor.getValue();
        assertEquals(1, productsFilterRequest.getCategory());
        assertTrue(productsFilterRequest.isDiscount());
        assertEquals(BigDecimal.valueOf(10.0), productsFilterRequest.getMinPrice());
        assertEquals(BigDecimal.valueOf(30.0), productsFilterRequest.getMaxPrice());
    }

    @Test
    void getProductsFilterByPriceRange() throws Exception {
        Page<ProductDto> page = new PageImpl<>(products, PageRequest.of(0, 3), products.size());
        when(service.getFiltered(any(ProductsFilterRequest.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/products")
                        .param("page", "0")  // default
                        .param("size", "3")
                        .param("minPrice", "15.0")
                        .param("maxPrice", "25.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(page)));

        verify(service).getFiltered(filterRequestArgumentCaptor.capture(), pageCaptor.capture());
        Pageable pageable = pageCaptor.getValue();
        assertEquals(3, pageable.getPageSize());
        assertEquals(0, pageable.getPageNumber());
        assertTrue(pageable.getSort().getOrderFor("name").isAscending());

        ProductsFilterRequest productsFilterRequest = filterRequestArgumentCaptor.getValue();
        assertEquals(BigDecimal.valueOf(15.0), productsFilterRequest.getMinPrice());
        assertEquals(BigDecimal.valueOf(25.0), productsFilterRequest.getMaxPrice());


    }

    @Test
    void getProductsDefaultPaginationByCategory() throws Exception {
        Page<ProductDto> page = new PageImpl<>(products, PageRequest.of(0, 5), products.size());
        when(service.getFiltered(any(ProductsFilterRequest.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("category", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(page)));

        ArgumentCaptor<Pageable> pageCaptor = ArgumentCaptor.forClass(Pageable.class);
        ArgumentCaptor<ProductsFilterRequest> requestCaptor = ArgumentCaptor.forClass(ProductsFilterRequest.class);
        verify(service).getFiltered(requestCaptor.capture(), pageCaptor.capture());
        Pageable pageable = pageCaptor.getValue();
        assertEquals(5, pageable.getPageSize());
        assertEquals(0, pageable.getPageNumber());
        assertTrue(pageable.getSort().getOrderFor("name").isAscending());

        ProductsFilterRequest productsFilterRequest = requestCaptor.getValue();
        assertEquals(1, productsFilterRequest.getCategory());
        assertNull(productsFilterRequest.getMinPrice());
        assertNull(productsFilterRequest.getMaxPrice());
        assertFalse(productsFilterRequest.isDiscount());
    }

    @Test
    void getByIdWhenIdExists() throws Exception {
        when(service.getById(2L)).thenReturn(charcoalProduct);
        mockMvc.perform(get("/products/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(charcoalProduct)));
        verify(service).getById(2L);
    }

    @Test
    void getByIdWhenIdNotExists() throws Exception {
        when(service.getById(20L)).thenThrow(new ResourceNotFoundException(String.format("Product with id %d not found", 20)));
        mockMvc.perform(get("/products/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string("Product with id 20 not found"));

        verify(service).getById(20L);
    }

    @Test
    void getByIdWithInvalidIdFormat() throws Exception {
        mockMvc.perform(get("/products/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductOfTheDay() throws Exception {
        when(service.getProductOfTheDay()).thenReturn(tulipProduct);

        mockMvc.perform(get("/products/productOfTheDay")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(tulipProduct)));

        verify(service).getProductOfTheDay();
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void createProductWithCorrectData() throws Exception {
        ArgumentCaptor<ProductDto> productDtoCaptor = ArgumentCaptor.forClass(ProductDto.class);

        ProductDto productCreateDto = new ProductDto(null, "Chamomile", "Description4", new BigDecimal("1.50"), 1L, "https://image4.jpeg", null);
        when(service.create(any())).thenAnswer(x -> {
            productCreateDto.setId(5L);
            return productCreateDto;
        });
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(productCreateDto)));

        verify(service).create(productDtoCaptor.capture());
        ProductDto productDto = productDtoCaptor.getValue();
        assertNull(productDto.getId());
        assertEquals(1, productDto.getCategoryId());
        assertEquals("Chamomile", productDto.getName());
        assertEquals("Description4", productDto.getDescription());
        assertEquals("https://image4.jpeg", productDto.getImage());
        assertEquals(new BigDecimal("1.50"), productDto.getPrice());
        assertNull(productDto.getDiscountPrice());
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void createProductWithIncorrectName() throws Exception {
        ProductDto productCreateDto = new ProductDto(null, "  ", "Description4", new BigDecimal("1.50"), 1L, "https://image4.jpeg", null);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Name should not be null, should be in latin letters and no more than 45 symbols\"}"));

        verify(service, never()).create(eq(productCreateDto));
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void createProductWithoutDescription() throws Exception {
        ArgumentCaptor<ProductDto> productDtoCaptor = ArgumentCaptor.forClass(ProductDto.class);

        ProductDto productCreateDto = new ProductDto(null, "Chamomile", null, new BigDecimal("1.50"), 1L, "https://image4.jpeg", null);
        when(service.create(any())).thenAnswer(x -> {
            productCreateDto.setId(5L);
            return productCreateDto;
        });
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(productCreateDto)));

        verify(service).create(productDtoCaptor.capture());
        ProductDto productDto = productDtoCaptor.getValue();
        assertNull(productDto.getId());
        assertEquals(1, productDto.getCategoryId());
        assertEquals("Chamomile", productDto.getName());
        assertNull(productDto.getDescription());
        assertEquals("https://image4.jpeg", productDto.getImage());
        assertEquals(new BigDecimal("1.50"), productDto.getPrice());
        assertNull(productDto.getDiscountPrice());
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void createProductWithIncorrectPrice() throws Exception {
        ProductDto productCreateDto = new ProductDto(null, "Chamomile", "Description4", new BigDecimal("0.00"), 1L, "https://image4.jpeg", null);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"price\":\"Price is required and must be greater than 0\"}"));

        verify(service, never()).create(eq(productCreateDto));
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void createProductWithoutCategory() throws Exception {
        ProductDto productCreateDto = new ProductDto(null, "Chamomile", "Description4", new BigDecimal("1.50"), null, "https://image4.jpeg", null);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"categoryId\":\"Category id is required\"}"));

        verify(service, never()).create(any());
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void createProductWithIncorrectImage() throws Exception {
        ProductDto productCreateDto = new ProductDto(null, "Chamomile", "Description4", new BigDecimal("1.50"), 1L, "http://image4.jpeg", null);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"image\":\"ImageURL should be valid\"}"));

        verify(service, never()).create(eq(productCreateDto));
    }

    @WithMockUser(username = "admin", roles = {"CLIENT"})
    @Test
    void createProductByNotAdmin() throws Exception {
        ProductDto productCreateDto = new ProductDto(null, "Chamomile", "Description4", new BigDecimal("1.50"), 1L, "https://image4.jpeg", null);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)));
        verify(service, never()).create(eq(productCreateDto));
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void updateProductWithCorrectData() throws Exception {
        ProductDto productUpdateDto = new ProductDto(3L, "Shovel", "New shovel Description", new BigDecimal("36.00"), 5L, "https://image3.jpeg", null);
        when(service.update(eq(3L), eq(productUpdateDto))).thenReturn(productUpdateDto);
        mockMvc.perform(put("/products/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(productUpdateDto)));

        verify(service).update(eq(3L), eq(productUpdateDto));

        ArgumentCaptor<ProductDto> productDtoCaptor = ArgumentCaptor.forClass(ProductDto.class);

        verify(service).update(eq(3L), productDtoCaptor.capture());
        ProductDto productDto = productDtoCaptor.getValue();
        assertEquals(3, productDto.getId());
        assertEquals(5, productDto.getCategoryId());
        assertEquals("Shovel", productDto.getName());
        assertEquals(productUpdateDto, productDto);
        assertEquals(new BigDecimal("36.00"), productDto.getPrice());
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void updateProductWithIncorrectData() throws Exception {
        Map<String, String> expectedErrors = Map.of(
                "name", "Name should not be null, should be in latin letters and no more than 45 symbols",
                "price", "Price is required and must be greater than 0",
                "categoryId", "Category id is required",
                "image", "ImageURL should be valid",
                "discountPrice", "Discount price must be greater than 0"
        );
        ProductDto productUpdateDto = new ProductDto(3L, "Shovel Embrace natural aesthetics with our Wicker Pot.", "New shovel Description", new BigDecimal("0.00"), null, "http://image3.jpeg", new BigDecimal("0.00"));

        mockMvc.perform(put("/products/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(expectedErrors)));

        verify(service, never()).update(eq(3L), eq(productUpdateDto));
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void updateProductWithIncorrectPrice() throws Exception {
        ProductDto productUpdateDto = new ProductDto(3L, "Shovel", "New shovel Description", new BigDecimal("0.00"), 5L, "https://image3.jpeg", null);
        mockMvc.perform(put("/products/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"price\":\"Price is required and must be greater than 0\"}"));
        verify(service, never()).update(eq(3L), eq(productUpdateDto));
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void setDiscountPriceWithCorrectData() throws Exception {
        ProductDto productResponseDto = new ProductDto(3L, "Shovel", "New shovel Description", new BigDecimal("36.00"), 5L, "https://image3.jpeg", new BigDecimal("26.00"));
        when(service.setDiscountPrice(any(), any())).thenReturn(productResponseDto);
        mockMvc.perform(patch("/products/3")
                        .param("discountPrice", "26.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(productResponseDto)));

        verify(service).setDiscountPrice(eq(3L), eq(new BigDecimal("26.00")));
        assertEquals(new BigDecimal("26.00"), productResponseDto.getDiscountPrice());
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void setDiscountPriceWithIncorrectDiscountPrice() throws Exception {
        mockMvc.perform(patch("/products/2")
                        .param("discountPrice", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"setDiscountPrice.discountPrice\":\"Discount price must be greater than 0\"}"));

        verify(service, never()).setDiscountPrice(eq(2L), any(BigDecimal.class));
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void resetDiscountPriceWhenDiscountPriceNotSent() throws Exception {
        ProductDto productResponseDto = new ProductDto(3L, "Shovel", "New shovel Description", new BigDecimal("36.00"), 5L, "https://image3.jpeg", null);
        when(service.setDiscountPrice(3L, null)).thenReturn(productResponseDto);
        mockMvc.perform(patch("/products/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(productResponseDto)));

        verify(service).setDiscountPrice(3L, null);
        assertNull(productResponseDto.getDiscountPrice());
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).delete(2L);
    }
}