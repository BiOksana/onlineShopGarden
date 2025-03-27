package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.CartDto;
import de.telran.onlineshopgarden.dto.CartItemAddDto;
import de.telran.onlineshopgarden.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CartDto> getByUserId(@RequestParam Integer userId) {
        // TODO: JWT аутентификация
        return new ResponseEntity<>(service.getByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addItem(@Valid @RequestBody CartItemAddDto dto, @RequestParam int userId) {
        // todo: replace with real userId
        service.addItem(dto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByUserId(@RequestParam Integer userId) {
        //todo: replace with real userId
        service.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }
}