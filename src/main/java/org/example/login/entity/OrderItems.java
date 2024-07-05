package org.example.login.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Products product;

    private int quantity;
    private double price;
}