package org.example.login.service;

import org.example.login.dto.Request.OrderRequest;
import org.example.login.dto.Request.PaymentsRequest;
import org.example.login.entity.*;
import org.example.login.repository.*;
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

    @Autowired
    private PaymentsRepo paymentsRepo;

    @Autowired
    private ProductsRepo productsRepo;

    @Transactional
    public Orders processOrder(OrderRequest orderRequest, PaymentsRequest paymentRequest, long userId) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ShoppingCart> cartItems = shoppingCartRepo.findByUsersUserId(userId);
        List<OrderItems> orderItems = new ArrayList<>();

        for (ShoppingCart cartItem : cartItems) {
            Products product = cartItem.getProducts();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("재고가 부족합니다.");
            }

            OrderItems orderItem = OrderItems.builder()
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();
            orderItems.add(orderItem);

            product.setStock(product.getStock() - cartItem.getQuantity());
            productsRepo.save(product);
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
                .total(orderRequest.getTotal())
                .orderItems(orderItems)
                .build();

        orderRepo.save(order);

        for (OrderItems item : orderItems) {
            item.setOrder(order);
            orderItemsRepo.save(item);
        }

        Payments payment = Payments.builder()
                .order(order)
                .paymentMethod(paymentRequest.getPaymentMethod())
                .paymentAmount(order.getTotal())
                .createdAt(LocalDateTime.now())
                .build();

        paymentsRepo.save(payment);

        shoppingCartRepo.deleteAll(cartItems);

        return order;
    }
    public List<Orders> findByUsersUserId(long userId) {
        return orderRepo.findByUsersUserId(userId);
    }

    public long countByOrderStatus(Long userId, String status) {
        return orderRepo.countByUsersUserIdAndOrderStatus(userId, status);
    }

    public List<Orders> findOrdersWithItemsAndProducts(long userId) {
        return ordersRepo.findOrdersWithItemsAndProductsByUserId(userId);
}
