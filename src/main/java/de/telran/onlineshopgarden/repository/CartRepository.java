package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Cart;
import de.telran.onlineshopgarden.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findCartByUser(User user);

    void deleteByUser(User user);
}