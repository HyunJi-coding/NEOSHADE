package org.example.login.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
    private long productId;
    private String name;
    private String description;
    private long price;
    private long stock;
    private String img;
    private LocalDateTime createdAt;
    private CategoriesResponse categories;

}
