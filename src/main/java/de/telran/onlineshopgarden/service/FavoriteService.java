package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.entity.Favorite;
import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.entity.User;
import de.telran.onlineshopgarden.mapper.ProductMapper;
import de.telran.onlineshopgarden.repository.FavoriteRepository;
import de.telran.onlineshopgarden.repository.ProductRepository;
import de.telran.onlineshopgarden.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthService authService;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository,
                           ProductRepository productRepository,
                           ProductMapper productMapper, AuthService authService) {
        this.favoriteRepository = favoriteRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.authService = authService;
    }

    public List<ProductDto> getFavoriteProducts() {
        User user = authService.getCurrentUser();
        List<Product> productList = favoriteRepository.findByUserUserId(user.getUserId()).stream()
                .map(Favorite::getProduct)
                .collect(Collectors.toList());
        return productMapper.entityListToDtoList(productList);
    }

    @Transactional
    public void addToFavorites(Integer productId) {
        User user = authService.getCurrentUser();
        if (favoriteRepository.existsByUserUserIdAndProductProductId(user.getUserId(), productId)) {
            return;
        }

        Product product = productRepository.getReferenceById(productId);

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFromFavorites(Integer productId) {
        User user = authService.getCurrentUser();
        favoriteRepository.deleteByUserUserIdAndProductProductId(user.getUserId(), productId);
    }
}