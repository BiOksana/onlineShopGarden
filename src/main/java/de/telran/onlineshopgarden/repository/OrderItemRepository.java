package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
