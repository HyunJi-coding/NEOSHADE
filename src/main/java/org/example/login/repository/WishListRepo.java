package org.example.login.repository;

import org.example.login.entity.Users;
import org.example.login.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepo extends JpaRepository<WishList,Long> {
    List<WishList> findByUsersUserId(Long userId);
    WishList findByWishlistIdAndUsersUserId(Long id, Long userId);
}
