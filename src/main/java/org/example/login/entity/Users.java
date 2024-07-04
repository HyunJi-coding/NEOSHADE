package org.example.login.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String username;

    private String email;

    private String password;

    private String address;

    private String phone;

    private String role;

    @OneToMany(mappedBy = "users")
    private List<ShoppingCart> shoppingCarts;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (role == null) {
            role = "USER";
        }
    }
}
