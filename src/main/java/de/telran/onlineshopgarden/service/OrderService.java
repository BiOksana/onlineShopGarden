package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.OrderCreateDto;
import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.entity.Order;
import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.entity.User;
import de.telran.onlineshopgarden.entity.enums.OrderStatus;
import de.telran.onlineshopgarden.exception.AuthorizationDeniedException;
import de.telran.onlineshopgarden.exception.IllegalOrderStatusException;
import de.telran.onlineshopgarden.exception.ResourceNotFoundException;
import de.telran.onlineshopgarden.mapper.OrderMapper;
import de.telran.onlineshopgarden.repository.OrderRepository;
import de.telran.onlineshopgarden.repository.ProductRepository;
import de.telran.onlineshopgarden.repository.UserRepository;
import de.telran.onlineshopgarden.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;
    private final AuthService authService;

    @Autowired
    public OrderService(OrderRepository repository, UserRepository userRepository, ProductRepository productRepository, OrderMapper mapper, AuthService authService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.authService = authService;
    }

    public List<OrderDto> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public OrderDto getById(Integer orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d not found", orderId)));

        return mapper.entityToDto(order);
    }

    public List<OrderDto> getOrderHistory() {
        String login = authService.getAuthInfo().getLogin();
        User user = userRepository.findUserByEmail(login).get();
        List<Order> orders = repository.findAllByUserUserId(user.getUserId());
        return mapper.entityListToDtoList(orders);
    }

    @Transactional
    public OrderDto create(OrderCreateDto orderCreateDto) {
        String login = authService.getAuthInfo().getLogin();
        User user = userRepository.findUserByEmail(login).get();
        Order order = mapper.createDtoToEntity(orderCreateDto);
        order.getOrderItems().forEach(item -> {
            Integer productId = item.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d not found", productId)));
            BigDecimal priceAtPurchase;
            if (product.getDiscountPrice() != null) {
                priceAtPurchase = product.getDiscountPrice();
            } else {
                priceAtPurchase = product.getPrice();
            }
            item.setOrder(order);
            item.setPriceAtPurchase(priceAtPurchase);
        });
        order.setUser(userRepository.getReferenceById(user.getUserId()));
        return mapper.entityToDto(repository.save(order));
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        String login = authService.getAuthInfo().getLogin();
        User user = userRepository.findUserByEmail(login).get();
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d not found", orderId)));

        if (!order.getUser().getUserId().equals(user.getUserId())) {
            throw new AuthorizationDeniedException("You cannot cancel orders that don't belong to you");
        }

        if (!(order.getStatus() == OrderStatus.CREATED || order.getStatus() == OrderStatus.PENDING_PAYMENT)) {
            throw new IllegalOrderStatusException("Order cannot be canceled in status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELED);
    }

    @Transactional
    public void anonymizeUserDataInOrders(Integer userId) {
        List<Order> orders = repository.findAllByUserUserId(userId);

        if (orders.stream().anyMatch(o -> o.getStatus() == OrderStatus.CREATED || o.getStatus() == OrderStatus.PENDING_PAYMENT || o.getStatus() == OrderStatus.PAID)) {
            throw new IllegalOrderStatusException("User cannot be deleted because there is an active order pending payment or already paid.");
        }

        for (Order order : orders) {
            order.setDeliveryAddress("Address removed");
            order.setContactPhone("0000000000");
        }
    }
}