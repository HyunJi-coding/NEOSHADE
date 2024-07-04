package org.example.login.repository;

import org.example.login.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepo extends JpaRepository<ShoppingCart,Long> {
    ShoppingCart findByUsersUserIdAndProductsProductId(long userId, long productId);
    List<ShoppingCart> findByUsersUserId(long userId);
}
