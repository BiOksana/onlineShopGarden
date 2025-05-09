package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.dto.ProductsFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:#{#filterRequest.category} IS NULL OR p.category.id = :#{#filterRequest.category}) AND " +
            "(:#{#filterRequest.minPrice} IS NULL OR p.price >= :#{#filterRequest.minPrice}) AND " +
            "(:#{#filterRequest.maxPrice} IS NULL OR p.price <= :#{#filterRequest.maxPrice}) AND " +
            "(:#{#filterRequest.discount} IS FALSE OR p.discountPrice IS NOT NULL)")
    Page<Product> findProducts(@Param("filterRequest") ProductsFilterRequest filterRequest, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.price > 0 AND p.discountPrice IS NOT NULL " +
            "ORDER BY (1 - p.discountPrice / p.price) DESC " +
            "LIMIT 1")
    Optional<Product> findProductWithHighestDiscount();

    boolean existsByCategoryId(Long categoryId);
}