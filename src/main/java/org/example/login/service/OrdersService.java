package org.example.login.service;

import org.example.login.dto.OrderRequest;
import org.example.login.entity.OrderItems;
import org.example.login.entity.Orders;
import org.example.login.entity.ShoppingCart;
import org.example.login.entity.Users;
import org.example.login.repository.OrderItemsRepo;
import org.example.login.repository.OrdersRepo;
import org.example.login.repository.ShoppingCartRepo;
import org.example.login.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private ShoppingCartRepo shoppingCartRepo;

    @Autowired
    private OrdersRepo orderRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Transactional
    public Orders processOrder(OrderRequest orderRequest, long userId) {
        Users user = usersRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<ShoppingCart> cartItems = shoppingCartRepo.findByUsersUserId(userId);
        List<OrderItems> orderItems = new ArrayList<>();

        for (ShoppingCart cartItem : cartItems) {
            OrderItems orderItem = OrderItems.builder()
                    .product(cartItem.getProducts())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProducts().getPrice())
                    .build();
            orderItems.add(orderItem);
        }

        Orders order = Orders.builder()
                .users(user)
                .recipientName(orderRequest.getRecipientName())
                .phoneNumber(orderRequest.getPhoneNumber())
                .address(orderRequest.getAddress())
                .detailedAddress(orderRequest.getDetailedAddress())
                .postalCode(orderRequest.getPostalCode())
                .deliveryRequest(orderRequest.getDeliveryRequest())
                .impUid(orderRequest.getImpUid())
                .merchantUid(orderRequest.getMerchantUid())
                .orderItems(orderItems)
                .build();

        orderRepo.save(order);

        for (OrderItems item : orderItems) {
            item.setOrder(order);
            orderItemsRepo.save(item);
        }

        shoppingCartRepo.deleteAll(cartItems);

        return order;
    }
}
