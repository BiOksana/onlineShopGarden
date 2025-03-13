package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.OrderItemDto;
import de.telran.onlineshopgarden.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {
    private final OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<OrderItemDto> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderItemDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
