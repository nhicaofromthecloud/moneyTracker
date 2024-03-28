package com.example.moneyM.repository;

import com.example.moneyM.model.UserAccount;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // Custom query methods can be defined here if needed e.g.
	  Optional<UserAccount> findByEmail(String email);
	  Optional<UserAccount> findByUsername(String username);
}
