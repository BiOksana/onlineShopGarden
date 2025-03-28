package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT MAX(p.price - p.discountPrice) FROM Product p WHERE p.discountPrice IS NOT NULL AND p.discountPrice < p.price")
    BigDecimal findMaxDiscountValue();

    @Query("SELECT p FROM Product p WHERE p.discountPrice IS NOT NULL AND (p.price - p.discountPrice) = :maxDiscount")
    List<Product> findProductsWithMaxDiscount(@Param("maxDiscount") BigDecimal maxDiscount);
}
