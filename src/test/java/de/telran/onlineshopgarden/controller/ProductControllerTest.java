package de.telran.onlineshopgarden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.onlineshopgarden.config.SecurityConfig;
import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.security.JwtProvider;
import de.telran.onlineshopgarden.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void getAllProducts() throws Exception {
        ProductDto product = new ProductDto();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(23.05));

        Pageable pageable = PageRequest.of(0, 5);
        when(service.getFiltered(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(product), pageable, 1));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());

        verify(service).getFiltered(any(), any(Pageable.class));
    }

    @WithMockUser(username = "admin", roles = {"ADMINISTRATOR"})
    @Test
    void getProductOfTheDay() throws Exception {
        ProductDto product = new ProductDto();
        product.setName("Product of the Day");

        when(service.getProductOfTheDay()).thenReturn(product);

        mockMvc.perform(get("/products/productOfTheDay"))
                .andExpect(status().isOk());

        verify(service).getProductOfTheDay();
    }
}
