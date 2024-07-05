package org.example.login.dto;

import java.util.List;

public class OrderRequest {
    private String recipientName;
    private String phoneNumber;
    private String address;
    private String detailedAddress;
    private String postalCode;
    private String deliveryRequest;
    private List<OrderItemRequest> items;
}
