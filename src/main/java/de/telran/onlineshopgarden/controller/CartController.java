package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.CartDto;
import de.telran.onlineshopgarden.dto.CartItemAddDto;
import de.telran.onlineshopgarden.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "REST API for managing cart in the app")
public class CartController {
    private final CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @Operation(summary = "Get cart by user id")
    @GetMapping
    public ResponseEntity<CartDto> getByUserId(@RequestParam Integer userId) {
        // TODO: JWT аутентификация
        return new ResponseEntity<>(service.getByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Add item to cart")
    @PostMapping
    public ResponseEntity<Void> addItem(@Valid @RequestBody CartItemAddDto dto, @RequestParam int userId) {
        // todo: replace with real userId
        service.addItem(dto, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove item from cart")
    @DeleteMapping
    public ResponseEntity<Void> deleteByUserId(@RequestParam Integer userId) {
        //todo: replace with real userId
        service.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }
}