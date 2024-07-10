package org.example.login.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.login.entity.Orders;
import org.hibernate.criterion.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    private long orderId;
    private String recipientName;
    private String phoneNumber;
    private String address;
    private String detailedAddress;
    private String postalCode;
    private String deliveryRequest;
    private String orderStatus;
    private String impUid;
    private String merchantUid;
    private double total;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public static OrdersResponse fromEntity(Orders orders) {
        List<OrderItemResponse> orderItemResponses = orders.getOrderItems().stream()
                .map(OrderItemResponse::fromEntity)
                .collect(Collectors.toList());

        return OrdersResponse.builder()
                .orderId(orders.getOrderId())
                .recipientName(orders.getRecipientName())
                .phoneNumber(orders.getPhoneNumber())
                .address(orders.getAddress())
                .detailedAddress(orders.getDetailedAddress())
                .postalCode(orders.getPostalCode())
                .deliveryRequest(orders.getDeliveryRequest())
                .orderStatus(orders.getOrderStatus())
                .impUid(orders.getImpUid())
                .merchantUid(orders.getMerchantUid())
                .total(orders.getTotal())
                .createdAt(orders.getCreatedAt())
                .items(orderItemResponses)
                .build();
    }

}
