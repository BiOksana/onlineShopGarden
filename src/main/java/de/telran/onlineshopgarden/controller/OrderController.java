package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    //TODO return STATUS
    public ResponseEntity<OrderDto> getById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(service.getById(orderId));
    }
}
