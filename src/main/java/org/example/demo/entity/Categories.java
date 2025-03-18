package org.example.login.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    private String name;

    @OneToMany(mappedBy = "categories")
    @ToString.Exclude
    private List<Products> products;
}
