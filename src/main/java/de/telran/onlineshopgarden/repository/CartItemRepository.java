package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Transactional
    @Modifying
    Integer deleteByProductId(Integer productId);
}
