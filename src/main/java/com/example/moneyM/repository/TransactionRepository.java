package com.example.moneyM.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moneyM.model.Category;
import com.example.moneyM.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
	List<Transaction> findByCategory(Category category);
	
	List<Transaction> findByType(String type);     
	
	List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
