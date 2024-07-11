package org.example.login.service;

import org.example.login.comm.Cm_encryp;
import org.example.login.entity.Users;
import org.example.login.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepo usersRepo;

    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<Users> selectAll() {
        return usersRepo.findAll();
    }

    public Users selectEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    public void insert(Users users) {
        Cm_encryp cm_encryp = new Cm_encryp();
        users.setPassword(cm_encryp.encryptSha256(users.getPassword()));
        usersRepo.save(users);
    }

    public Users getUserById(Long userId) {
        return usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateUser(Long userId, String password, String gender, String birthDay) {
        Users users = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (password != null && !password.isEmpty()) {
            Cm_encryp cm_encryp = new Cm_encryp();
            String hashedPassword = cm_encryp.encryptSha256(password);
            users.setPassword(hashedPassword);
        }

        // 성별과 생년월일은 항상 업데이트
        users.setGender(gender);
        users.setBirthDay(birthDay);

        usersRepo.save(users);
    }

    public void delete(long keyId) {
        usersRepo.deleteById(keyId);
    }
}
