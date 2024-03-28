package com.example.moneyM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moneyM.model.Budget;
import com.example.moneyM.model.Category;
import com.example.moneyM.model.UserAccount;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
//	Optional<Budget> findByBudgetCategory(String category);

    Optional<Budget> findByCategoryAndUserAccount(Category category, UserAccount userAccount);
    List<Budget> findByUserAccountUserId(Long userId);
    Optional<Budget> findByCategoryCategoryId(Long categoryId);
}
