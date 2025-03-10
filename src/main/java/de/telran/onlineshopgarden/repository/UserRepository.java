package de.telran.onlineshopgarden.repository;

import de.telran.onlineshopgarden.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("DELETE FROM User u WHERE u.userId = :id")
    @Modifying
    void deleteByIdWithoutSelect(Integer id);
}
