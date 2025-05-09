package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.controller.api.FavoriteControllerApi;
import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController implements FavoriteControllerApi {

    private final FavoriteService service;

    @Autowired
    public FavoriteController(FavoriteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getUserFavorites() {
        List<ProductDto> favorites = service.getFavoriteProducts();
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addToFavorites(@RequestParam Long productId) {
        service.addToFavorites(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFromFavorites(@RequestParam Long productId) {
        service.removeFromFavorites(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}