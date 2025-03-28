package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUserId(Integer userId);

    void deleteByUserId(Integer userId);
}