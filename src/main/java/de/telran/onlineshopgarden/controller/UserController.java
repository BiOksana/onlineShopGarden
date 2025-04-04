package de.telran.onlineshopgarden.controller;

import de.telran.onlineshopgarden.dto.*;
import de.telran.onlineshopgarden.security.AuthService;
import de.telran.onlineshopgarden.service.UserService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final AuthService authService;

    @Autowired
    public UserController(UserService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/all")
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("{userId}")
    public UserDto getById(@PathVariable Integer userId) {
        return service.getById(userId);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PutMapping("{userId}")
    public ResponseEntity<UserDto> update(@PathVariable Integer userId, @Valid @RequestBody UserUpdateDto dto) {
        return new ResponseEntity<>(service.update(userId, dto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> mask(@PathVariable Integer userId) {
        service.mask(userId);
        return ResponseEntity.ok().build();
    }
}