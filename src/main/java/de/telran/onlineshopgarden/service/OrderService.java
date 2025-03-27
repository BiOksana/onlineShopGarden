package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.OrderCreateDto;
import de.telran.onlineshopgarden.dto.OrderDto;
import de.telran.onlineshopgarden.entity.Order;
import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.entity.enums.OrderStatus;
import de.telran.onlineshopgarden.exception.IllegalOrderStatusException;
import de.telran.onlineshopgarden.exception.ResourceNotFoundException;
import de.telran.onlineshopgarden.mapper.OrderMapper;
import de.telran.onlineshopgarden.repository.OrderRepository;
import de.telran.onlineshopgarden.repository.ProductRepository;
import de.telran.onlineshopgarden.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    @Autowired
    public OrderService(OrderRepository repository, UserRepository userRepository, ProductRepository productRepository, OrderMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<OrderDto> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public OrderDto getById(Integer orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d not found", orderId)));

        return mapper.entityToDto(order);
    }

    public List<OrderDto> getOrderHistory(Integer userId) {
        List<Order> orders = repository.findAllByUserUserId(userId);
        return mapper.entityListToDtoList(orders);
    }

    @Transactional
    public OrderDto create(OrderCreateDto orderCreateDto, int userId) {
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
        order.setUser(userRepository.getReferenceById(userId));
        return mapper.entityToDto(repository.save(order));
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d not found", orderId)));

        if (!(order.getStatus() == OrderStatus.CREATED || order.getStatus() == OrderStatus.PENDING_PAYMENT)) {
            throw new IllegalOrderStatusException("Order cannot be canceled in status: " + order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELED);
    }
}
