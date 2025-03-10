package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<ProductDto> getAll() {
        return service.getAll();
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getById(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.getById(productId));
    }

}
