package com.example.moneyM.service;

import java.time.LocalDate;
//import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.dto.TransactionDto;
import com.example.moneyM.dto.TransactionResponse;
import com.example.moneyM.model.Category;
import com.example.moneyM.model.Transaction;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.CategoryRepository;
import com.example.moneyM.repository.TransactionRepository;
import com.example.moneyM.repository.UserAccountRepository;


@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private UserAccountRepository userAccountRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public TransactionService(TransactionRepository transactionRepository, 
							  UserAccountRepository userAccountRepository,
							  CategoryRepository categoryRepository) {
		this.transactionRepository = transactionRepository;
		this.userAccountRepository = userAccountRepository;
		this.categoryRepository = categoryRepository;
	}

	public TransactionDto createTransaction(TransactionDto transactionDto) {
		UserAccount userAccount = userAccountRepository.findById(transactionDto.getUserId())
                .orElseThrow(() -> new RuntimeException("UserAccount not found"));
		
		//convert DTO to Entity
		Transaction transactionRequest = modelMapper.map(transactionDto, Transaction.class);
		
		// set UserAccount for Transaction entity
		transactionRequest.setUserAccount(userAccount);
		
		// convert dto String date to LocalDate
		LocalDate date = LocalDate.parse(transactionDto.getDate());
		transactionRequest.setDate(date);
		
		Transaction transaction = transactionRepository.save(transactionRequest);
		
		// convert Entity to DTO
		TransactionDto transactionResponse = modelMapper.map(transaction, TransactionDto.class);
		
		return transactionResponse;
	}
	
	public List<TransactionResponse> getAllTransactions() {
		 List<Transaction> transactionList = transactionRepository.findAll();
		    return transactionList
		            .stream()
		            .map(transaction -> modelMapper.map(transaction,  TransactionResponse.class))
		            .collect(Collectors.toList());
	
	}
	

    public TransactionResponse getTransactionById(Long id) {
    	  Transaction transaction = transactionRepository.findById(id)
    	            .orElseThrow(() -> new RuntimeException(("Transaction not found")));
      
    	// convert Entity to DTO
  		TransactionResponse transactionResponse = modelMapper.map(transaction, TransactionResponse.class);
  		
  		return transactionResponse;
    }
	
	public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
		
		Transaction transaction = transactionRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Transaction not found"));
		
		  Category category = categoryRepository.findById(transactionDto.getCategoryId())
	                .orElseThrow(() -> new RuntimeException("Category not found"));
		
		transaction.setDescription(transactionDto.getDescription());
		transaction.setAmount(transactionDto.getAmount());
		transaction.setCategory(category);
		transaction.setDate(LocalDate.parse(transactionDto.getDate()));
		
		// save the updated transaction entity
		Transaction updatedTransaction = transactionRepository.save(transaction);
		
		// convert the updated transaction entity to DTO
	    TransactionDto updatedTransactionDto = modelMapper.map(updatedTransaction, TransactionDto.class);
	    
		return updatedTransactionDto;
	}
	
	public void deleteTransaction(Long id) {
		transactionRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Transaction not found for id: " + id));
		
		transactionRepository.deleteById(id);
	}
	
}
