package org.example.login.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Orders order;

    private String paymentMethod;
    private String paymentStatus;
    private double paymentAmount;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (paymentMethod == null) {
            paymentMethod = "card";
        }
        if (paymentStatus == null) {
            paymentStatus = "Complete";
        }
    }


}
