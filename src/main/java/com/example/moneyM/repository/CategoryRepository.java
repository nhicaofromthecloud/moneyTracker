package com.example.moneyM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moneyM.model.Category;
import com.example.moneyM.model.UserAccount;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findCategoryByName(String name);
	Optional<Category> findCategoryByType(String type);
	List<Category> findByUserAccountUserId(Long userId);
    Optional<Category> findCategoryByNameAndUserAccountUserId(String name, Long userId);
    Optional<Category> findByUserAccountAndNameIgnoreCaseAndType(UserAccount userAccount, String name, String type);
}
