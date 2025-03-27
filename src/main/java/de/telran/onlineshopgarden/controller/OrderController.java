package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.OrderCreateDto;
import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderDto> getById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(service.getById(orderId));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderHistory(@PathVariable Integer userId) {
        List<OrderDto> orderHistory = service.getOrderHistory(userId);
        return ResponseEntity.ok(orderHistory);
    }

    @PostMapping("{userId}")
    //TODO JWT user
    public ResponseEntity<OrderDto> create(@Valid @RequestBody OrderCreateDto orderCreateDto, @PathVariable int userId) {
        return new ResponseEntity<>(service.create(orderCreateDto, userId), HttpStatus.CREATED);
    }

    @PatchMapping("{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Integer orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}