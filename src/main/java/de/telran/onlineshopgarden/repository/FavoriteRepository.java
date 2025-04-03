package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    List<Favorite> findByUserUserId(Integer userId);

    boolean existsByUserUserIdAndProductProductId(Integer userId, Integer productId);

    @Modifying
    void deleteByUserUserIdAndProductProductId(Integer userId, Integer productId);

    @Modifying
    void deleteByProductProductId(Integer productId);
}