package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.entity.Order;
import de.telran.onlineshopgarden.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return repository.findById(id);
    }

}
