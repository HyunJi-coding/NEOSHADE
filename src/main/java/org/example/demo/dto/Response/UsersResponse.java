package org.example.login.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.login.entity.ShoppingCart;
import org.example.login.entity.Users;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
    private String username;
    private String email;
    private String password;
    private String gender;
    private String birthDay;

    public static UsersResponse fromEntity(Users users) {
        return UsersResponse.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .gender(users.getGender())
                .email(users.getEmail())
                .birthDay(users.getBirthDay())
                .build();
    }
}