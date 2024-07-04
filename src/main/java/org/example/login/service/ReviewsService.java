package org.example.login.service;

import org.example.login.entity.Products;
import org.example.login.entity.Reviews;
import org.example.login.entity.Users;
import org.example.login.repository.ProductsRepo;
import org.example.login.repository.ReviewsRepo;
import org.example.login.repository.UsersRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewsService {
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;
    private final ReviewsRepo reviewsRepo;

    public ReviewsService(ReviewsRepo reviewsRepo,
                               UsersRepo usersRepo,
                               ProductsRepo productsRepo) {
        this.reviewsRepo = reviewsRepo;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
    }

    public List<Reviews> findByUsersUserId(long userId) {
        return reviewsRepo.findByUsersUserId(userId);
    }

    public List<Reviews> findByProductsProductId(long userId) {
        return reviewsRepo.findByProductsProductId(userId);
    }

    public Page<Reviews> getReviewsByProductId(long productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewsRepo.findByProductsProductId(pageable, productId);
    }


    public void insert(Reviews reviews, long userId, long productId) {
        Users user = usersRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Products product = productsRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        reviews.setUsers(user);
        reviews.setProducts(product);
        reviews.setCreatedAt(LocalDateTime.now());

        reviewsRepo.save(reviews);
    }
}
