package org.example.login.service;

import org.example.login.comm.Cm_encryp;
import org.example.login.dto.Request.UsersRequest;
import org.example.login.dto.Response.UsersResponse;
import org.example.login.entity.Users;
import org.example.login.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {
    private final UsersRepo usersRepo;

    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<UsersResponse> selectAll() {
        List<Users> usersList = usersRepo.findAll();
        return usersList.stream()
                .map(UsersResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public void insert(UsersRequest usersRequest) {
        Users user = usersRequest.toEntity();
        Cm_encryp cm_encryp = new Cm_encryp();
        user.setPassword(cm_encryp.encryptSha256(user.getPassword()));
        usersRepo.save(user);
    }

    public UsersResponse getUserById(Long userId) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UsersResponse.fromEntity(user);
    }

    public void updateUser(Long userId, String password, String gender, String birthDay) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (password != null && !password.isEmpty()) {
            Cm_encryp cm_encryp = new Cm_encryp();
            String hashedPassword = cm_encryp.encryptSha256(password);

            user.setPassword(hashedPassword);
        }

        user.setGender(gender);
        user.setBirthDay(birthDay);

        usersRepo.save(user);
    }

    public void deleteUser(long keyId) {
        usersRepo.deleteById(keyId);
    }

    public UsersResponse selectEmail(String email) {
        Users user = usersRepo.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return UsersResponse.fromEntity(user);
    }

}
