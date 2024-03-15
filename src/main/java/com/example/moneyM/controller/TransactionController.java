package com.example.moneyM.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moneyM.dto.TransactionDto;
import com.example.moneyM.dto.TransactionResponse;
import com.example.moneyM.service.TransactionService;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
		
		TransactionDto newTransaction = transactionService.createTransaction(transactionDto);
		return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
	};
	
	@GetMapping
	public ResponseEntity<List<TransactionResponse>> getAllTransactions(@RequestParam(required = false) Long userId,
            													@RequestParam(required = false) String description) {
		List<TransactionResponse> transactions = transactionService.getAllTransactions(userId, description);
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
		TransactionResponse transaction = transactionService.getTransactionById(id);
		return ResponseEntity.ok(transaction); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id,
														@RequestBody TransactionDto transactionDto) {
		
		TransactionDto updatedTransaction = transactionService.updateTransaction(id, transactionDto);
		return ResponseEntity.ok(updatedTransaction); 
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
		 transactionService.deleteTransaction(id);
		 return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/filter")
	public ResponseEntity<List<TransactionResponse>> getTransactionsByDateRange(
			@RequestParam("userId") Long userId,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<TransactionResponse> transactions = transactionService.getTransactionsByDateRange(userId, startDate, endDate);
		return ResponseEntity.ok(transactions);
	}


	
}
