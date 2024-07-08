package org.example.login.repository;

import org.example.login.entity.Payments;
import org.example.login.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepo extends JpaRepository<Payments,Long> {

}
