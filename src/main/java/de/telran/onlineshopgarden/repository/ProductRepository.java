package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);
}
