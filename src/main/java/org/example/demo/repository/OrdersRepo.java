package org.example.login.repository;

import org.example.login.entity.Orders;
import org.example.login.entity.Products;
import org.example.login.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {
    List<Orders> findByUsersUserId(long userId);
    long countByUsersUserIdAndOrderStatus(Long userId, String status);

    @Query("SELECT DISTINCT o FROM Orders o " +
            "JOIN FETCH o.orderItems oi " +
            "JOIN FETCH oi.product " +
            "WHERE o.users.userId = :userId")
    List<Orders> findOrdersWithItemsAndProductsByUserId(@Param("userId") long userId);
}
