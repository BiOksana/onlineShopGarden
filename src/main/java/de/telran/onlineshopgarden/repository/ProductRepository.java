package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
