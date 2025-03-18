package org.example.login.service;

import org.example.login.entity.Products;
import org.example.login.entity.ShoppingCart;
import org.example.login.entity.Users;
import org.example.login.repository.ProductsRepo;
import org.example.login.repository.ShoppingCartRepo;
import org.example.login.repository.UsersRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartService {
    private final UsersRepo usersRepo;
    private final ProductsRepo productsRepo;
    private final ShoppingCartRepo shoppingCartRepo;

    public ShoppingCartService(ShoppingCartRepo shoppingCartRepo,
                               UsersRepo usersRepo,
                               ProductsRepo productsRepo) {
        this.shoppingCartRepo = shoppingCartRepo;
        this.usersRepo = usersRepo;
        this.productsRepo = productsRepo;
    }

    public List<ShoppingCart> findByUsersUserId(long userId) {
        return shoppingCartRepo.findByUsersUserId(userId);
    }
    public void insert(long userId, long productId){
        Users user = usersRepo.findById(userId).get();
        Products product = productsRepo.findById(productId).get();

        ShoppingCart existingCart = shoppingCartRepo.findByUsersUserIdAndProductsProductId(userId, productId);
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + 1);
            shoppingCartRepo.save(existingCart);
        } else {
            ShoppingCart newCart = ShoppingCart.builder()
                    .users(user)
                    .products(product)
                    .quantity(1)
                    .createdAt(LocalDateTime.now())
                    .build();
            shoppingCartRepo.save(newCart);
        }
    }
}
