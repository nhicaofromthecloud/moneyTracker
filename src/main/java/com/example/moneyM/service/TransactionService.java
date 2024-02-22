package com.example.moneyM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.model.Transaction;
import com.example.moneyM.model.TransactionRepository;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	public List<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}
	
}
