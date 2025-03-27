package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Order;
import de.telran.onlineshopgarden.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserUserId(Integer userId);

    List<Order> findByStatusNotIn(List<OrderStatus> statuses);
}
