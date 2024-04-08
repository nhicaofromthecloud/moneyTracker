package com.example.moneyM.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.dto.BudgetDto;
import com.example.moneyM.dto.BudgetResponse;
import com.example.moneyM.model.Budget;
import com.example.moneyM.model.Category;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.BudgetRepository;
import com.example.moneyM.repository.CategoryRepository;
import com.example.moneyM.repository.UserAccountRepository;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	//Create new budget
	public BudgetDto createBudget(BudgetDto budgetDto) {
		// Validate user account existence
		Optional<UserAccount> userAccountOptional = userAccountRepository.findById(budgetDto.getUserId());
		UserAccount userAccount;
		if (userAccountOptional.isPresent()) {
			userAccount = userAccountOptional.get();
		} else {
			throw new RuntimeException("Invalid User");
		}

		// Validate category existence
		Optional<Category> categoryOptional = categoryRepository.findById(budgetDto.getCategoryId());
		Category category;
		if (categoryOptional.isPresent()) {
			category = categoryOptional.get();
		} else {
			throw new RuntimeException("Invalid Category");
		}

		//convert DTO to Entity
		Budget budgetRequest = modelMapper.map(budgetDto, Budget.class);

		//set UserAccount for Budget entity
		budgetRequest.setUserAccount(userAccount);

		//set Category for Budget entity
		budgetRequest.setCategory(category);

//		Optional<Budget> existingBudget = budgetRepository.findByCategoryAndUserAccount(budgetRequest.getCategory(), budgetRequest.getUserAccount());
		
		  // Check if budget already exists for the given category and user account combination
	    Optional<Budget> existingBudget = budgetRepository.findByCategoryAndUserAccount(category, userAccount);

		if (existingBudget.isPresent()) {
			throw new RuntimeException("Budget for this category already exists for the user, please update existing category instead");
		}

		Budget budget = budgetRepository.save(budgetRequest);

		//convert Entity to DTO
		BudgetDto budgetResponse = modelMapper.map(budget, BudgetDto.class);

		return budgetResponse;
	}



	//Get all budget
	public List<BudgetResponse> getAllBudgets(Long userId) {
		List<Budget> budgetList = new ArrayList<Budget>();
		if(userId == null) {
			budgetList = budgetRepository.findAll();
		} else if (userId != null) {
			budgetList = budgetRepository.findByUserAccountUserId(userId);
		}
		return budgetList
				.stream()
				.map(budget -> modelMapper.map(budget, BudgetResponse.class))
				.collect(Collectors.toList());
	}

	//Get a budget by ID
	public BudgetResponse getBudgetById(Long id) {
		Budget budget = budgetRepository.findById(id).orElseThrow(() -> new RuntimeException("Budget not found for id :: " + id));
		//convert Entity to DTO
		BudgetResponse budgetResponse = modelMapper.map(budget, BudgetResponse.class);

		return budgetResponse;
	}

	//Update a budget
	public BudgetDto updateBudget(Long id, BudgetDto budgetDto) {
		Budget budget = budgetRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Budget already defined, please edit the existing one"));

		Category category = categoryRepository.findById(budgetDto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not found"));

		budget.setAmount(budgetDto.getAmount());
		budget.setCategory(category);
		budget.setTimeFrame(budgetDto.getTimeFrame());

		//save budget entity
		Budget updatedBudget = budgetRepository.save(budget);

		//convert updated budget to entity DTO
		BudgetDto updatedBudgetDto = modelMapper.map(updatedBudget, BudgetDto.class);

		return updatedBudgetDto;



	}

	//Delete a budget
	public void deleteBudget(Long id) {

		Budget budget = budgetRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Budget not found"));
		budgetRepository.delete(budget);
	}
}
