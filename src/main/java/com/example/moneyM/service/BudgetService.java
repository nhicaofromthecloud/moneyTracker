package com.example.moneyM.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	//Create new budget
	public Budget createBudget(Budget budget) { 

		// Validate user account existence
		Optional<UserAccount> userAccountOptional = userAccountRepository.findById(budget.getUserAccount().getUserId());
		UserAccount userAccount;
		if (userAccountOptional.isPresent()) {
		    userAccount = userAccountOptional.get();
		} else {
		    throw new IllegalArgumentException("Invalid User");
		}

		// Validate category existence
		Optional<Category> categoryOptional = categoryRepository.findById(budget.getCategory().getCategoryId());
		Category category;
		if (categoryOptional.isPresent()) {
		    category = categoryOptional.get();
		} else {
		    throw new IllegalArgumentException("Invalid Category");
		}

        // Set validated user account and category
        budget.setUserAccount(userAccount);
        budget.setCategory(category);
        
		Optional<Budget> existingBudget = budgetRepository.findByCategoryAndUserAccount(budget.getCategory(), budget.getUserAccount());

        if (existingBudget.isPresent()) {
            throw new RuntimeException("Budget for this category already exists for the user");
        }

        return budgetRepository.save(budget);

	}

	//Get all budget
	public List<Budget> getAllBudgets() {
		return budgetRepository.findAll();
	}

	//Get a budget by ID
	public Budget getBudgetById(Long id) {
		Optional<Budget> budget = budgetRepository.findById(id);
        return budget.orElseThrow(() -> new RuntimeException("Budget not found for id :: " + id));
	}
	
	//Update a budget
	public Budget updateBudget(Long id, Budget budgetDetails) {
		Budget budget = getBudgetById(id); // Reuse getBudgetById to ensure the budget exists
		
		budget.setAmount(budgetDetails.getAmount());
		budget.setTimeFrame(budget.getTimeFrame());
		
		return budgetRepository.save(budget);
	}
	
	//Delete a budget
    public void deleteBudget(Long id) {
        Budget budget = getBudgetById(id); // Reuse getBudgetById to ensure the budget exists
        budgetRepository.delete(budget);
    }
}
