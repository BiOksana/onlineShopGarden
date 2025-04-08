package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.OrderCreateDto;
import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "REST API for managing orders in the app")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(summary = "Get all orders")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get order by id")
    @GetMapping("{orderId}")
    public ResponseEntity<OrderDto> getById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(service.getById(orderId));
    }

    @Operation(summary = "Get order history by user id")
    @GetMapping("/history")
    public ResponseEntity<List<OrderDto>> getOrderHistory() {
        List<OrderDto> orderHistory = service.getOrderHistory();
        return ResponseEntity.ok(orderHistory);
    }

    @Operation(summary = "Create new order")
    @PostMapping
    public ResponseEntity<OrderDto> create(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        return new ResponseEntity<>(service.create(orderCreateDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Cancel order")
    @PatchMapping("{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Integer orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}