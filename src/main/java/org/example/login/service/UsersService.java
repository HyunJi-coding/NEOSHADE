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

    public Users selectOne(long keyId) {
        return usersRepo.findById(keyId).get();
    }

    public Users selectEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    public void insert(Users users) {
        Cm_encryp cm_encryp = new Cm_encryp();
        users.setPassword(cm_encryp.encryptSha256(users.getPassword()));
        usersRepo.save(users);
    }

    public void update(Users users) {
        usersRepo.save(users);
    }

    public void delete(long keyId) {
        usersRepo.deleteById(keyId);
    }
}
