package org.example.login.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.login.entity.Reviews;
import org.example.login.entity.WishList;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsResponse {
    private Long reviewId;
    private String title;
    private String comment;
    private String username;
    private String productname;
    private String productImg;
    private String category;
    private LocalDateTime createdAt;

    public static ReviewsResponse fromEntity(Reviews review) {
        return ReviewsResponse.builder()
                .reviewId(review.getReviewId())
                .title(review.getTitle())
                .comment(review.getComment())
                .username(review.getUsers().getUsername())
                .productname(review.getProducts().getName())
                .productImg(review.getProducts().getImg())
                .category(review.getProducts().getCategories().getName())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewsResponse fromEntity2(Reviews review) {
        return ReviewsResponse.builder()
                .reviewId(review.getReviewId())
                .title(review.getTitle())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .username(review.getUsers().getUsername())
                .build();
    }


}