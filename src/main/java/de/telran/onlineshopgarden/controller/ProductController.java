package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.controller.api.ProductControllerApi;
import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.dto.ProductsFilterRequest;
import de.telran.onlineshopgarden.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController implements ProductControllerApi {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ProductDto> getProducts(
            @ParameterObject ProductsFilterRequest filterRequest,
            @ParameterObject @PageableDefault(size = 5, sort = "name") Pageable pageable
    ) {
        return service.getFiltered(filterRequest, pageable);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getById(productId));
    }

    @GetMapping("/productOfTheDay")
    public ResponseEntity<ProductDto> getProductOfTheDay() {
        return ResponseEntity.ok(service.getProductOfTheDay());
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping()
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("{productId}")
    public ResponseEntity<ProductDto> update(@PathVariable Long productId, @Valid @RequestBody ProductDto dto) {
        return new ResponseEntity<>(service.update(productId, dto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PatchMapping("{productId}")
    public ResponseEntity<ProductDto> setDiscountPrice(@PathVariable Long productId,
                                                       @DecimalMin(value = "0.01", message = "{validation.product.discountPrice}") @RequestParam(required = false) BigDecimal discountPrice) {
        ProductDto updatedProduct = service.setDiscountPrice(productId, discountPrice);
        return ResponseEntity.ok(updatedProduct);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        service.delete(productId);
        return ResponseEntity.ok().build();
    }
}