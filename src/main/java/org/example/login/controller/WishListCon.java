package org.example.login.controller;

import org.example.login.dto.Request.WishListRequest;
import org.example.login.dto.Response.ShoppingCartResponse;
import org.example.login.dto.Response.WishListResponse;
import org.example.login.entity.ShoppingCart;
import org.example.login.entity.WishList;
import org.example.login.repository.WishListRepo;
import org.example.login.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/wishlist")
public class WishListCon {
    private final WishListService wishListService;

    @Autowired
    public WishListCon(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public String getWishList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/users/login";
        }

        List<WishListResponse> wishList = wishListService.getWishList(userId);
        model.addAttribute("wishList", wishList);

        return "/member/wishlist";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToWishList(HttpServletRequest request, @RequestParam Long productId) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 다시 시도해주세요.");
        }

        try {
            wishListService.addWishList(userId, productId);
            return ResponseEntity.ok().body("위시리스트에 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteWishList(@RequestParam Long wishListId) {
        wishListService.deleteWishList(wishListId);
        return ResponseEntity.ok().build();
    }


}