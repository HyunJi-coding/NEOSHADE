package org.example.login.entity;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String name;
    private String description;
    private int price;
    private int stock;
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    @ToString.Exclude
    private Categories categories;

}
