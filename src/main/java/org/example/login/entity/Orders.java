package org.example.login.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;

    private String recipientName;
    private String phoneNumber;
    private String address;
    private String detailedAddress;
    private String postalCode;
    private String deliveryRequest;
    private String paymentStatus;
    private double total;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (paymentStatus == null) {
            paymentStatus = "PENDING";
        }
        total = orderItems != null ? orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum() : 0.0;
    }
}


