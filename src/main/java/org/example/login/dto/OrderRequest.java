package org.example.login.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String recipientName;
    private String phoneNumber;
    private String address;
    private String detailedAddress;
    private String postalCode;
    private String deliveryRequest;
    private String impUid;
    private String merchantUid;
    private List<OrderItemRequest> items;
}
