package com.example.moneyM.repository;

import com.example.moneyM.model.Goal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    // Custom query methods can be defined here if needed, feg:
    // List<Goal> findByUserAccountId(Long userAccountId);
	List<Goal> findByUserAccountUserId(Long userId);
}
