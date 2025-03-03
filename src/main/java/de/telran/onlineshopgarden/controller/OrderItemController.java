package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.entity.OrderItem;
import de.telran.onlineshopgarden.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {
    private final OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.service = orderItemService;
    }

    @GetMapping("/all")
    public List<OrderItem> getAllOrderItems() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<OrderItem> getOrderItem(@PathVariable Integer id) {
        return service.getOrderItemById(id);
    }
}
