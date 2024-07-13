package org.example.login.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.login.entity.Products;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private Long stock;
    private String img;
    private LocalDateTime createdAt;
    private CategoriesResponse categories;

    public static ProductsResponse fromEntity(Products product) {
        return ProductsResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .img(product.getImg())
                .createdAt(product.getCreatedAt())
                .categories(new CategoriesResponse(product.getCategories().getName()))
                .build();
    }

}
