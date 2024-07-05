package org.example.login.controller;

import org.example.login.entity.ShoppingCart;
import org.example.login.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderCon {
    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping
    public String getUserShoppingCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/secure/login";
        }

        List<ShoppingCart> shoppingCart = shoppingCartService.findByUsersUserId(userId);
        long totalPrice = shoppingCart.stream()
                .mapToLong(item -> item.getQuantity() * item.getProducts().getPrice())
                .sum();

        model.addAttribute("shoppingCartItems", shoppingCart);
        model.addAttribute("totalPrice", totalPrice);
        return "/member/order";
    }
}
