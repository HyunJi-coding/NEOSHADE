package org.example.login.service;

import org.example.login.dto.Response.ProductsResponse;
import org.example.login.entity.Products;
import org.example.login.repository.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private final ProductsRepo productsRepo;

    @Autowired
    public ProductsService(ProductsRepo productsRepository) {
        this.productsRepo = productsRepository;
    }

    public List<Products> selectAll() {
        return productsRepo.findAll();
    }

    public List<Products> findLatestProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        return productsRepo.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Products selectOne(long productId) {
        return productsRepo.findById(productId).orElse(null);
    }

    public List<Products> searchByKeyword(String keyword) {
        return productsRepo.findByCategoriesNameContainingOrNameContaining(keyword, keyword);
    }

    public Products insert(Products product) {
        return productsRepo.save(product);
    }

    public void delete(Long productId) {
        productsRepo.deleteById(productId);
    }
}
