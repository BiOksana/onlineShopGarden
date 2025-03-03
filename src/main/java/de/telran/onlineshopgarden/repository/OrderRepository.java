package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
