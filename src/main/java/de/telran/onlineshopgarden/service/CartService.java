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
import de.telran.onlineshopgarden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CartService {
    private final CartRepository repository;
    private final CartMapper mapper;
    private final CartItemMapper cartItemMapper;

    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository repository, CartMapper mapper, CartItemMapper cartItemMapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.cartItemMapper = cartItemMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addItem(CartItemAddDto dto, int userId) {
        User user = userRepository.getReferenceById(userId);

        final Cart cart = repository.findCartByUser(user)
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

    public CartDto getByUserId(Integer userId) {
        User user = userRepository.getReferenceById(userId);
        return repository.findCartByUser(user)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cart by user with id %d not found", userId)));
    }

    @Transactional
    public void deleteByUserId(Integer userId) {
        User user = userRepository.getReferenceById(userId);
        repository.deleteByUser(user);
    }
}