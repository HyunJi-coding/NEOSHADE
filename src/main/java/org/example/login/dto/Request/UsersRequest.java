package org.example.login.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}