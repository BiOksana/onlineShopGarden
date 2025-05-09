package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.CategoryDto;
import de.telran.onlineshopgarden.entity.Category;
import de.telran.onlineshopgarden.exception.ResourceDeletionException;
import de.telran.onlineshopgarden.exception.ResourceNotFoundException;
import de.telran.onlineshopgarden.mapper.CategoryMapper;
import de.telran.onlineshopgarden.repository.CategoryRepository;
import de.telran.onlineshopgarden.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository repository, CategoryMapper mapper, ProductRepository productRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    public List<CategoryDto> getAll() {
        return mapper.entityListToDto(repository.findAll());
    }

    public CategoryDto getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %d not found", id)));
        return mapper.entityToDto(category);
    }

    @Transactional
    public CategoryDto create(CategoryDto dto) {
        Category category = mapper.dtoToEntity(dto);
        return mapper.entityToDto(repository.save(category));
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %d not found", id)));
        category.setName(dto.getName());
        category.setImageUrl(dto.getImageUrl());
        return mapper.entityToDto(category);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Category with id %d not found", id));
        }

        if (productRepository.existsByCategoryId(id)) {
            throw new ResourceDeletionException(String.format("Category with id %d cannot be deleted because it has associated products", id));
        }

        repository.deleteById(id);
    }
}