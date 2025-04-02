package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.OrderItemFullDto;
import de.telran.onlineshopgarden.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderitems")
@Tag(name = "Order Items", description = "REST API for managing order items in the app")
public class OrderItemController {
    private final OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @Operation(summary = "Get all order items")
    @GetMapping("/all")
    public ResponseEntity<List<OrderItemFullDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get order item by id")
    @GetMapping("{id}")
    public ResponseEntity<OrderItemFullDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
