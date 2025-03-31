package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    boolean existsByProductId(Integer productId);
}