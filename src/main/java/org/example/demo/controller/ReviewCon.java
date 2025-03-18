package org.example.login.controller;

import org.example.login.dto.Request.ReviewRequest;
import org.example.login.entity.Products;
import org.example.login.entity.Reviews;
import org.example.login.service.ProductsService;
import org.example.login.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ProductsService productsService;

    @GetMapping
    public String getUserReviews(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/secure/login";
        }

        List<Reviews> reviews = reviewsService.findByUsersUserId(userId);
        model.addAttribute("reviews", reviews);
        return "/member/reviewlist";
    }


    @GetMapping("/{productId}")
    public String getProductReviews(@PathVariable long productId, Model model) {
        List<Reviews> productReviews = reviewsService.findByProductsProductId(productId);
        model.addAttribute("reviews", productReviews);
        return "/review/list";
    }

    @GetMapping("/write/{productId}")
    public String getReviewWritePage(@PathVariable long productId, Model model) {
        Products products = productsService.selectOne(productId);

        model.addAttribute("productId", productId);
        model.addAttribute("productImg", products.getImg());
        model.addAttribute("productName", products.getName());
        model.addAttribute("productCategory", products.getCategories().getName());

        return "/member/reviewwrite";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(HttpServletRequest request, @RequestParam long productId,
                                            @RequestBody ReviewRequest reviewRequest) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 다시 시도해주세요.");
        }

        try {
            Reviews review = new Reviews();
            review.setTitle(reviewRequest.getTitle());
            review.setComment(reviewRequest.getComment());
            reviewsService.insert(review, userId, productId);
            return ResponseEntity.ok().body("리뷰가 작성되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
