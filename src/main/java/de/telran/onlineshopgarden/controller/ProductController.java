package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable Integer id) {
        return service.getProductById(id);
    }

    @GetMapping("/searchByName")
    public List<Product> searchByName(@RequestParam String name) {
        return service.getProductsByName(name);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        Product addedProduct = service.addProduct(product);
        return ResponseEntity.ok(addedProduct);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        Product updatedProduct = service.updateProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.accepted().build();
    }
}
