package com.example.moneyM.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moneyM.model.Category;
import com.example.moneyM.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
	Optional<Transaction> findByCategory(Category category);
	
	List<Transaction> findByUserAccountUserIdAndDescriptionContainingIgnoreCase(Long userId, String description);
	
	List<Transaction> findByUserAccountUserId(Long userId);
	
	List<Transaction> findAllByUserAccountUserIdAndDateBetween(Long userId, 
			LocalDate startDate, LocalDate endDate);
	
// customized query sample
//	@Query(value = "SELECT t FROM Transaction t WHERE t.userAccount.userId = :id AND t.date BETWEEN :startDate AND :endDate")
//	List<Transaction> findAllByUserAccountUserIdAndDateBetween(@Param("id") Long userId, 
//							@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	

}
