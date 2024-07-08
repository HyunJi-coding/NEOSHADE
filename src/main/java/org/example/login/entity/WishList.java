package org.example.login.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long wishlistId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ToString.Exclude
    private Users users;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @ToString.Exclude
    private Products products;

    private LocalDateTime createdAt;

}
