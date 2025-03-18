package org.example.login.repository;

import org.example.login.entity.Products;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
    List<Products> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Products> findByCategoriesNameContainingOrNameContaining(String categoryKeyword, String nameKeyword);
}