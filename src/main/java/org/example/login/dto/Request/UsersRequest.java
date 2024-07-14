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

    public static Users fromEntity(UsersRequest userRequest) {
        return Users.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .gender(userRequest.getGender())
                .birthDay(userRequest.getBirthDay())
                .build();
    }
}