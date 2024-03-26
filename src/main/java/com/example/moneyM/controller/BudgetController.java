package com.example.moneyM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.moneyM.dto.BudgetDto;
import com.example.moneyM.dto.BudgetResponse;
import com.example.moneyM.model.Budget;
import com.example.moneyM.service.BudgetService;

@RestController
@RequestMapping("api/budgets")
public class BudgetController {

	@Autowired
	private BudgetService budgetService;
	
	//Create new budget
	@PostMapping
	public ResponseEntity<BudgetDto> createBudget(@RequestBody BudgetDto budgetDto) {
		BudgetDto newBudget = budgetService.createBudget(budgetDto);
		return new ResponseEntity<>(newBudget, HttpStatus.CREATED);
	}

	//Get all budget
	@GetMapping
	public ResponseEntity<List<BudgetResponse>> getAllBudgets(@RequestParam(required = false) Long userId) {
		List<BudgetResponse> budgets = budgetService.getAllBudgets(userId);
		return ResponseEntity.ok(budgets);
	}
	
	//Get a single budget by id
	@GetMapping("/{id}")
	public ResponseEntity<BudgetResponse> getBudgetById(@PathVariable Long id) {
		BudgetResponse budget = budgetService.getBudgetById(id);
		return ResponseEntity.ok(budget);
	}
	
	//Update a category
	@PutMapping("/{id}")
	public ResponseEntity<BudgetDto> updateBudget(@PathVariable Long id, @RequestBody BudgetDto budgetDto) {
		BudgetDto updatedBudget = budgetService.updateBudget(id, budgetDto);
		return ResponseEntity.ok(updatedBudget);
	}
	
	//Delete a budget
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
		budgetService.deleteBudget(id);
		return ResponseEntity.noContent().build();
	}
}
