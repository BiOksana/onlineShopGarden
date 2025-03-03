package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.entity.Category;
import de.telran.onlineshopgarden.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Optional<Category> getCategoryById(Integer id) {
        return repository.findById(id);
    }

    public List<Category> getCategoryByName(String name) {
        return repository.findByName(name);

    }

    @Transactional
    public Category addCategory(Category category) {
        return repository.save(category);
    }

    @Transactional
    public Category updateCategory(Category category) {
        Integer id = category.getCategoryId();
        Optional<Category> categoryOptional = repository.findById(id);
        if (categoryOptional.isPresent()) {
            return repository.save(category);
        }
        return null;
    }

    @Transactional
    public void deleteCategory(Integer id) {
        repository.deleteById(id);
    }

    public void changeName(Integer id, String name) {
        repository.findById(id).ifPresent(category -> {
            category.setName(name);
            repository.save(category);
        });
    }
}
