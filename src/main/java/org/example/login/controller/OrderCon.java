package org.example.login.controller;

import org.example.login.dto.Response.OrderItemResponse;
import org.example.login.dto.Response.OrdersResponse;
import org.example.login.dto.Response.ShoppingCartResponse;
import org.example.login.entity.Orders;
import org.example.login.entity.ShoppingCart;
import org.example.login.service.OrdersService;
import org.example.login.service.ReviewsService;
import org.example.login.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderCon {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    ReviewsService reviewsService;

    @GetMapping("/order")
    public String getOrderList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/secure/login";
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
        return "/member/order";
    }

    @GetMapping("/order/list")
    public String getUserOrderList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/users/login";
        }

        List<Orders> orders = ordersService.findByUsersUserId(userId);
        List<OrdersResponse> ordersResponseList = orders.stream()
                .map(order -> {
                    OrdersResponse response = OrdersResponse.fromEntity(order);
                    List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                            .map(item -> {
                                OrderItemResponse itemResponse = OrderItemResponse.fromEntity(item);
                                itemResponse.setReviewed(reviewsService.hasUserReviewedProduct(userId, item.getProduct().getProductId()));
                                return itemResponse;
                            })
                            .collect(Collectors.toList());
                    response.setItems(itemResponses);
                    return response;
                })
                .collect(Collectors.toList());

        model.addAttribute("orders", ordersResponseList);
        return "/member/orderlist";
    }

    @GetMapping("/mypage")
    public String getOrderStatus(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return "redirect:/secure/login";
        }

        long preparingCount = ordersService.countByOrderStatus(userId, "PREPARING");
        long shippingCount = ordersService.countByOrderStatus(userId, "SHIPPING");
        long deliveredCount = ordersService.countByOrderStatus(userId, "DELIVERED");

        model.addAttribute("preparingCount", preparingCount);
        model.addAttribute("shippingCount", shippingCount);
        model.addAttribute("deliveredCount", deliveredCount);

        return "/member/mypage";
    }


}
