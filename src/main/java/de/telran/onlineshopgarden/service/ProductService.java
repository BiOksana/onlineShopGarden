package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.exception.BadRequestException;
import de.telran.onlineshopgarden.exception.ResourceDeletionException;
import de.telran.onlineshopgarden.exception.ResourceNotFoundException;
import de.telran.onlineshopgarden.mapper.ProductMapper;
import de.telran.onlineshopgarden.repository.*;
import de.telran.onlineshopgarden.requests.ProductsFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public ProductService(ProductRepository repository,
                          ProductMapper mapper,
                          CategoryRepository categoryRepository,
                          OrderItemRepository orderItemRepository,
                          CartItemRepository cartItemRepository,
                          FavoriteRepository favoriteRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.favoriteRepository = favoriteRepository;
    }

    public List<ProductDto> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public Page<ProductDto> getFiltered(ProductsFilterRequest filterRequest, Pageable pageable) {
        return mapper.toDtoPage(repository.findProducts(filterRequest, pageable));
    }

    public ProductDto getById(Integer id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d not found", id)));
        return mapper.entityToDto(product);
    }

    @Transactional
    public ProductDto create(ProductDto dto) {
        Product product = mapper.createOrUpdateDtoToEntity(dto);
        product.setCategory(categoryRepository.getReferenceById(dto.getCategoryId()));
        return mapper.entityToDto(repository.save(product));
    }

    @Transactional
    public ProductDto update(Integer id, ProductDto dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Product with id %d not found", id));
        }

        Product product = mapper.createOrUpdateDtoToEntity(dto);
        product.setProductId(id);
        product.setCategory(categoryRepository.getReferenceById(dto.getCategoryId()));

        return mapper.entityToDto(repository.save(product));
    }

    @Transactional
    public ProductDto setDiscountPrice(Integer productId, BigDecimal discountPrice) {
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id %d not found", productId)));

        if (discountPrice != null && (discountPrice.compareTo(new BigDecimal("0.00")) <= 0 || discountPrice.compareTo(product.getPrice()) >= 0)) {
            throw new BadRequestException("Discount price must be bigger than 0 and smaller than " + product.getPrice());
        }

        product.setDiscountPrice(discountPrice);
        return mapper.entityToDto(product);
    }

    public ProductDto getProductOfTheDay() {
        return repository.findProductWithHighestDiscount()
                .map(mapper::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("No discounted products found"));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Product with id %d not found", id));
        }

        if (orderItemRepository.existsByProductId(id)) {
            throw new ResourceDeletionException(String.format("Product with id %d cannot be deleted because it has associated orders", id));
        }

        favoriteRepository.deleteByProductProductId(id);

        cartItemRepository.deleteByProductId(id);

        repository.deleteById(id);
    }
}