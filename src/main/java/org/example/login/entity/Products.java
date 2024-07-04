package org.example.login.entity;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    private String name;
    private String description;
    private long price;
    private long stock;
    private String img;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "products")
    private List<ShoppingCart> shoppingCart;

    @OneToMany(mappedBy = "products")
    private List<Reviews> reviews;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    @ToString.Exclude
    private Categories categories;

}
