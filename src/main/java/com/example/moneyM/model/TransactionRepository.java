package com.example.moneyM.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findByCategory(Category category);
	
	List<Transaction> findByType(String type);     
	
	List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
	
}
