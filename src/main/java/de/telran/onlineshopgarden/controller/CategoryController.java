package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.entity.Category;
import de.telran.onlineshopgarden.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.service = categoryService;
    }

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategory(@PathVariable Integer id) {
        return service.getCategoryById(id);
    }

    @GetMapping("/searchByName")
    public List<Category> searchByName(@RequestParam String name) {
        return service.getCategoryByName(name);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category) {
        Category addedCategory= service.addCategory(category);
        return ResponseEntity.ok(addedCategory);
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid Category category) {
        Category updatedCategory = service.updateCategory(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping
    public ResponseEntity<Void> changeNameCategory(@RequestParam Integer id, @RequestParam String name) {
        service.changeName(id, name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
