package com.example.moneyM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moneyM.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findCategoryByName(String name);
	Optional<Category> findCategoryByType(String type);

}
