package com.example.moneyM.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.model.Transaction;
import com.example.moneyM.repository.TransactionRepository;


@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	public Transaction createTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}
	

    public Transaction getTransactionById(Long id) {
    	Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            return transaction.get();
        } else {
            throw new RuntimeException("Transaction not found for id: " + id);
        }
    }
	
	public Transaction updateTransaction(Long id, Transaction transactionData) {
		
		Transaction transaction = getTransactionById(id);
		
		transaction.setDescription(transactionData.getDescription());
		transaction.setType(transactionData.getType());
		transaction.setAmount(transactionData.getAmount());
		transaction.setCategory(transactionData.getCategory());
		transaction.setDate(transactionData.getDate());
		
		
		return transactionRepository.save(transaction);
	}
	
	public void deleteTransaction(Long id) {
		transactionRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Transaction not found for id: " + id));
		
		transactionRepository.deleteById(id);
	}
	
}
