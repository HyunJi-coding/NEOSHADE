package org.example.login.repository;

import org.example.login.entity.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepo extends JpaRepository<Reviews, Long> {
    List<Reviews> findByUsersUserId(long userId);
    List<Reviews> findByProductsProductId(long productId);
    Page<Reviews> findByProductsProductId(Pageable pageable, long productId);
}
