package org.example.login.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.login.entity.Users;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequest {
    private String username;
    private String email;
    private String password;
    private String gender;
    private String birthDay;

    public Users toEntity() {
        return Users.builder()
                .username(username)
                .email(email)
                .password(password)
                .gender(gender)
                .birthDay(birthDay)
                .build();
    }
}