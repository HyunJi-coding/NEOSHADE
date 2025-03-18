package org.example.login.dto.Response;

import lombok.Builder;
import lombok.Data;
import org.example.login.entity.ShoppingCart;

import java.time.LocalDateTime;

@Data
@Builder
public class ShoppingCartResponse {
    private Long cartId;
    private Long productId;
    private Long userId;
    private String productName;
    private Long quantity;
    private LocalDateTime createdAt;
    private String productImg;
    private Long price;

    public static ShoppingCartResponse fromEntity(ShoppingCart shoppingCart) {
        return ShoppingCartResponse.builder()
                .cartId(shoppingCart.getCartId())
                .userId(shoppingCart.getUsers().getUserId())
                .productId(shoppingCart.getProducts().getProductId())
                .productName(shoppingCart.getProducts().getName())
                .quantity(shoppingCart.getQuantity())
                .createdAt(shoppingCart.getCreatedAt())
                .productImg(shoppingCart.getProducts().getImg())
                .price(shoppingCart.getProducts().getPrice())
                .build();
    }
}
