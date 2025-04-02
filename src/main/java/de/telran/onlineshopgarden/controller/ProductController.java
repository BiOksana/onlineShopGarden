package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.requests.ProductsFilterRequest;
import de.telran.onlineshopgarden.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "REST API for managing products in the app")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Get all products")
    @GetMapping("/all")
    public List<ProductDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get products filtered by name, category, price, discountPrice")
    @GetMapping
    public Page<ProductDto> getProducts(
            ProductsFilterRequest filterRequest,
            @PageableDefault(size = 5, sort = "name") Pageable pageable
    ) {
        return service.getFiltered(filterRequest, pageable);
    }

    @Operation(summary = "Get product by id")
    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getById(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.getById(productId));
    }

    @Operation(summary = "Create new product")
    @PostMapping()
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update product by id")
    @PutMapping("{productId}")
    public ResponseEntity<ProductDto> update(@PathVariable Integer productId, @Valid @RequestBody ProductDto dto) {
        return new ResponseEntity<>(service.update(productId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Set discount price for product")
    @PatchMapping("{productId}")
    public ResponseEntity<ProductDto> setDiscountPrice(@PathVariable Integer productId,
                                                       @RequestParam(required = false) BigDecimal discountPrice) {
        ProductDto updatedProduct = service.setDiscountPrice(productId, discountPrice);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Get product with highest discount")
    @GetMapping("/productOfTheDay")
    public ResponseEntity<ProductDto> getProductOfTheDay() {
        return ResponseEntity.ok(service.getProductOfTheDay());
    }
}
