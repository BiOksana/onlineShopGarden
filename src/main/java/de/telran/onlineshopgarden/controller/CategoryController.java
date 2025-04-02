package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.CategoryDto;
import de.telran.onlineshopgarden.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "REST API for managing category in the app")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get category by id")
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(service.getById(categoryId));
    }
    @Operation(summary = "Create new category")
    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }
    @Operation(summary = "Update category by id")
    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryDto> update(@PathVariable Integer categoryId, @Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(service.update(categoryId, dto));
    }
}