package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.controller.api.CategoryControllerApi;
import de.telran.onlineshopgarden.dto.CategoryDto;
import de.telran.onlineshopgarden.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController implements CategoryControllerApi {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(service.getById(categoryId));
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long categoryId, @Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(service.update(categoryId, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long categoryId) {
        service.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}