package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
