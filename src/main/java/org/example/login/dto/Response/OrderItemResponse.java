package org.example.login.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.login.entity.OrderItems;
import org.example.login.entity.WishList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private String productName;
    private String productImg;
    private String productCategory;
    private Long quantity;
    private double price;

    public static OrderItemResponse fromEntity(OrderItems orderItems) {
        return OrderItemResponse.builder()
                .productName(orderItems.getProduct().getName())
                .productImg(orderItems.getProduct().getImg())
                .productCategory(orderItems.getProduct().getCategories().getName())
                .quantity(orderItems.getQuantity())
                .price(orderItems.getPrice())
                .build();
    }
}
