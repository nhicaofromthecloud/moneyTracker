package com.example.moneyM.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moneyM.model.Category;
import com.example.moneyM.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
	Optional<Transaction> findByCategory(Category category);     
	
	Optional<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
