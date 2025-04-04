package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.OrderCreateDto;
import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderDto>> getOrderHistory() {
        List<OrderDto> orderHistory = service.getOrderHistory();
        return ResponseEntity.ok(orderHistory);
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        return new ResponseEntity<>(service.create(orderCreateDto), HttpStatus.CREATED);
    }

    @PatchMapping("{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Integer orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}