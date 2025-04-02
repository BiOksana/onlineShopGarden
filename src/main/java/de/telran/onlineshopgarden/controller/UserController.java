package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.UserCreateDto;
import de.telran.onlineshopgarden.dto.UserDto;
import de.telran.onlineshopgarden.dto.UserUpdateDto;
import de.telran.onlineshopgarden.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "REST API for managing users in the app")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Get all users")
    @GetMapping("/all")
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get user by id")
    @GetMapping("{userId}")
    public UserDto getById(@PathVariable Integer userId) {
        return service.getById(userId);
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Operation(summary = "Update user by id")
    @PutMapping("{userId}")
    public ResponseEntity<UserDto> update(@PathVariable Integer userId, @Valid @RequestBody UserUpdateDto dto) {
        return new ResponseEntity<>(service.update(userId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId) {
        service.delete(userId);
        return ResponseEntity.ok().build();
    }
}