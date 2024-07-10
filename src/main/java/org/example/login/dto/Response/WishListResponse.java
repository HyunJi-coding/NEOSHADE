package org.example.login.dto.Response;

import lombok.Builder;
import lombok.Data;
import org.example.login.entity.ShoppingCart;
import org.example.login.entity.WishList;

import java.time.LocalDateTime;

@Data
@Builder
public class WishListResponse {
    private Long wishlistId;
    private String productName;
    private String category;
    private String productImg;
    private Long price;
    private LocalDateTime createdAt;

    public static WishListResponse fromEntity(WishList wishList) {
        return WishListResponse.builder()
                .wishlistId(wishList.getWishlistId())
                .productName(wishList.getProducts().getName())
                .category(wishList.getProducts().getCategories().getName())
                .createdAt(wishList.getCreatedAt())
                .productImg(wishList.getProducts().getImg())
                .price(wishList.getProducts().getPrice())
                .build();
    }
}