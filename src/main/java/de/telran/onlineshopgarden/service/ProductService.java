package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return repository.findById(id);
    }

    public List<Product> getProductsByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    @Transactional
    public Product updateProduct(Product product) {
        Integer id = product.getProductId();
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            return repository.save(product);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteProduct(Integer id) {
        repository.deleteById(id);
    }
}
