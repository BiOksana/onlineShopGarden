package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.entity.Order;
import de.telran.onlineshopgarden.exception.ResourceNotFoundException;
import de.telran.onlineshopgarden.mapper.OrderMapper;
import de.telran.onlineshopgarden.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Autowired
    public OrderService(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<OrderDto> getAll() {
        return mapper.entityListToDto(repository.findAll());
    }

    public OrderDto getById(Integer orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d not found", orderId)));
        return mapper.entityToDto(order);
    }

}
