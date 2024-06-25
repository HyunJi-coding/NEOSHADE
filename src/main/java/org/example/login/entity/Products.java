package org.example.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    private int product_id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Categories categories;

}
