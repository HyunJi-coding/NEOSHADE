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
public class ReviewsResponse {
    private long reviewId;
    private String title;
    private String comment;
    private long rating;
    private String username;
    private long productId;
    private LocalDateTime createdAt;
}