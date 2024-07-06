package org.example.login.entity;

import lombok.*;

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
    @ToString.Exclude
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Products product;

    private long quantity;
    private double price;

}