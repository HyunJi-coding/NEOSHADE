package org.example.login.controller;

import org.example.login.dto.Request.ShoppingCartRequest;
import org.example.login.dto.Response.ShoppingCartResponse;
import org.example.login.entity.ShoppingCart;
import org.example.login.service.ShoppingCartService;
import org.example.login.util.PriceFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartCon {
    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping
    public String getShoppingCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/users/login";
        }

        List<ShoppingCart> shoppingCart = shoppingCartService.findByUsersUserId(userId);
        List<ShoppingCartResponse> shoppingCartResponseList = shoppingCart.stream()
                .map(ShoppingCartResponse::fromEntity)
                .collect(Collectors.toList());

        long totalPrice = shoppingCart.stream()
                .mapToLong(item -> item.getQuantity() * item.getProducts().getPrice())
                .sum();

        model.addAttribute("shoppingCartItems", shoppingCartResponseList);
        model.addAttribute("totalPrice", totalPrice);
        return "/member/shoppingcart";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addShoppingCart(HttpServletRequest request, @RequestParam long productId) {
        HttpSession session = request.getSession();
        System.out.println("Add to Cart - Session ID: " + session.getId());
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 다시 시도해주세요.");
        }

        try {
            shoppingCartService.insert(userId, productId);
            return ResponseEntity.ok().body("장바구니에 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}