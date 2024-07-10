package org.example.login.service;

import org.example.login.dto.Request.WishListRequest;
import org.example.login.dto.Response.WishListResponse;
import org.example.login.entity.Products;
import org.example.login.entity.Users;
import org.example.login.entity.WishList;
import org.example.login.repository.ProductsRepo;
import org.example.login.repository.UsersRepo;
import org.example.login.repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListService {
    private final WishListRepo wishListRepo;
    private final ProductsRepo productsRepo;
    private final UsersRepo usersRepo;

    @Autowired
    public WishListService(WishListRepo wishListRepo, ProductsRepo productsRepo, UsersRepo usersRepo) {
        this.wishListRepo = wishListRepo;
        this.productsRepo = productsRepo;
        this.usersRepo = usersRepo;
    }

    public WishListResponse addWishList(Long userId, Long productId) {
        Users user = usersRepo.findById(userId).get();
        Products product = productsRepo.findById(productId).get();


        WishList wishList = WishList.builder()
                .users(user)
                .products(product)
                .createdAt(LocalDateTime.now())
                .build();

        wishList = wishListRepo.save(wishList);

        return WishListResponse.fromEntity(wishList);
    }

    public List<WishListResponse> getWishList(Long userId) {
        List<WishList> wishList = wishListRepo.findByUsersUserId(userId);
        return wishList.stream()
                .map(WishListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteWishList(Long wishlistId) {
        wishListRepo.deleteById(wishlistId);
    }
}
