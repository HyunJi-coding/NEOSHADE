package org.example.login.controller;

import org.example.login.entity.Reviews;
import org.example.login.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewCon {
    @Autowired
    ReviewsService reviewsService;

    @GetMapping
    public String getUserReviews(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/secure/login";
        }

        List<Reviews> reviews = reviewsService.findByUsersUserId(userId);
        model.addAttribute("reviews", reviews);
        return "/review/reviewlist";
    }


    @GetMapping("/{productId}")
    public String getProductReviews(@PathVariable long productId, Model model) {
        List<Reviews> productReviews = reviewsService.findByProductsProductId(productId);
        model.addAttribute("reviews", productReviews);
        return "/review/list";
    }


    @PostMapping("/add")
    public ResponseEntity<String> addReview(HttpServletRequest request, @RequestParam long productId,
                                            @RequestParam String title,@RequestParam String comment,
                                            @RequestParam long rating) {
        HttpSession session = request.getSession();
        System.out.println("Add Review - Session ID: " + session.getId());
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 다시 시도해주세요.");
        }

        try {
            Reviews review = new Reviews();
            review.setTitle(title);
            review.setComment(comment);
            review.setRating(rating);
            reviewsService.insert(review, userId, productId);
            return ResponseEntity.ok().body("리뷰가 작성되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
