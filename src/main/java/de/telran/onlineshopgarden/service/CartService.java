package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.CartDto;
import de.telran.onlineshopgarden.dto.CartItemAddDto;
import de.telran.onlineshopgarden.entity.Cart;
import de.telran.onlineshopgarden.entity.CartItem;
import de.telran.onlineshopgarden.entity.User;
import de.telran.onlineshopgarden.exception.ResourceNotFoundException;
import de.telran.onlineshopgarden.mapper.CartItemMapper;
import de.telran.onlineshopgarden.mapper.CartMapper;
import de.telran.onlineshopgarden.repository.CartRepository;
import de.telran.onlineshopgarden.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository repository;
    private final CartMapper mapper;
    private final CartItemMapper cartItemMapper;
    private final AuthService authService;

    @Autowired
    public CartService(CartRepository repository, CartMapper mapper, CartItemMapper cartItemMapper, AuthService authService) {
        this.repository = repository;
        this.mapper = mapper;
        this.cartItemMapper = cartItemMapper;
        this.authService = authService;
    }

    public CartDto getByUserId() {
        User user = authService.getCurrentUser();
        return repository.findByUserId(user.getId())
                .map(mapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cart by user with id %d not found", user.getId())));
    }

    @Transactional
    public void addItem(CartItemAddDto dto) {
        User user = authService.getCurrentUser();
        final Cart cart = repository.findByUserId(user.getId())
                .orElse(new Cart(user));

        CartItem cartItem = cartItemMapper.dtoToEntity(dto);

        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(cartItem.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        i -> i.setQuantity(i.getQuantity() + dto.getQuantity()),
                        () -> cart.getItems().add(new CartItem(null, cart, cartItem.getProductId(), cartItem.getQuantity()))
                );

        repository.save(cart);
    }

    @Transactional
    public void deleteByUserId() {
        User user = authService.getCurrentUser();
        repository.deleteByUserId(user.getId());
    }
}