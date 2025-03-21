package de.telran.onlineshopgarden.service;

import de.telran.onlineshopgarden.dto.ProductDto;
import de.telran.onlineshopgarden.entity.Favorite;
import de.telran.onlineshopgarden.entity.Product;
import de.telran.onlineshopgarden.entity.User;
import de.telran.onlineshopgarden.mapper.ProductMapper;
import de.telran.onlineshopgarden.repository.FavoriteRepository;
import de.telran.onlineshopgarden.repository.ProductRepository;
import de.telran.onlineshopgarden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository,
                           ProductMapper productMapper) {
        this.favoriteRepository = favoriteRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public void addToFavorites(Integer userId, Integer productId) {
        if (favoriteRepository.existsByUserUserIdAndProductProductId(userId, productId)) {
            return;
        }

        User user = userRepository.getReferenceById(userId);
        Product product = productRepository.getReferenceById(productId);

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        favoriteRepository.save(favorite);
    }

    public List<ProductDto> getFavoriteProducts(Integer userId) {
        List<Product> productList = favoriteRepository.findByUserUserId(userId).stream()
                .map(Favorite::getProduct)
                .collect(Collectors.toList());
        return productMapper.entityListToDtoList(productList);
    }

    @Transactional
    public void removeFromFavorites(Integer userId, Integer productId) {
        favoriteRepository.deleteByUserUserIdAndProductProductId(userId, productId);
    }
}