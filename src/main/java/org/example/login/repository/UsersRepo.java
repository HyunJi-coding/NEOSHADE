package org.example.login.repository;

import org.example.login.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users,Integer> {
    Users findByEmail(String email);
}
