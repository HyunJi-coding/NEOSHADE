package org.example.login.service;

import org.example.login.entity.Products;
import org.example.login.repository.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Products selectOne(Long productId) {
        return productsRepo.findById(productId).orElse(null);
    }

    public Products insert(Products product) {
        return productsRepo.save(product);
    }

    public void delete(Long productId) {
        productsRepo.deleteById(productId);
    }
}
