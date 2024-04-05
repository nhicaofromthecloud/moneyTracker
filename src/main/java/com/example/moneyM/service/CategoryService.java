package com.example.moneyM.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.dto.CategoryDto;
import com.example.moneyM.dto.CategoryResponse;
import com.example.moneyM.model.Category;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.CategoryRepository;
import com.example.moneyM.repository.UserAccountRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private ModelMapper modelMapper;

	//Create new category
	public CategoryDto createCategory(CategoryDto categoryDto) {
		UserAccount userAccount = userAccountRepository.findById(categoryDto.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

//		Optional<Category> existingCategory = categoryRepository
//				.findByUserAccountUserIdAndNameContainingIgnoreCaseAndType(categoryDto.getUserId(), 
//						categoryDto.getName(), categoryDto.getType());

		 // Check if category already exists for the user
//	    Optional<Category> existingCategory = categoryRepository.findByUserAccountAndNameIgnoreCaseAndType(userAccount, categoryDto.getName(), categoryDto.getType());
	    Optional<Category> existingCategory = categoryRepository.findByUserAccountAndNameIgnoreCaseAndType(userAccount, categoryDto.getName(), categoryDto.getType()); 
	    
		if (existingCategory.isPresent()) {
			throw new RuntimeException("Category already exists for the user");
		}

		//convert DTO to Entity
		Category categoryRequest = modelMapper.map(categoryDto, Category.class);

		// set UserAccount for Category Entity
		categoryRequest.setUserAccount(userAccount);

		Category category = categoryRepository.save(categoryRequest);

		// convert Entity to DTO
		CategoryDto categoryResponse = modelMapper.map(category, CategoryDto.class);

		return categoryResponse;
	}

	//Get all category
	public List<CategoryResponse> getAllCategories(Long userId) {
		List<Category> categoryList = new ArrayList<Category>();
		if(userId == null) {
			categoryList = categoryRepository.findAll();
		}else {
			categoryList = categoryRepository.findByUserAccountUserId(userId);
		}
		return categoryList
				.stream()
				.map(category -> modelMapper.map(category, CategoryResponse.class))
				.collect(Collectors.toList());
	}

	//Get a category by ID
	public CategoryResponse getCategoryById(Long id) {
		
		Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found for id :: " + id));
		//convert entity to DTO
		CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

		return categoryResponse;
	}

	//update Category
	public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
		// Check if the category exists
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found"));
		UserAccount userAccount = userAccountRepository.findById(categoryDto.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// check for existing category
		Optional<Category> existingCategory = categoryRepository
				.findByUserAccountAndNameIgnoreCaseAndType(userAccount, 
						categoryDto.getName(), categoryDto.getType());
		
		if(existingCategory.isPresent()) {
			throw new RuntimeException("Category and Type already exists");
		}

		category.setName(categoryDto.getName());
		category.setType(categoryDto.getType());

		//save category entity
		Category updatedCategory = categoryRepository.save(category);

		//convert the updated category to entity dto
		CategoryDto updatedCategoryDto = modelMapper.map(updatedCategory, CategoryDto.class);

		return updatedCategoryDto;

	}


	// Delete a category
	public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found for id :: " + id));
		categoryRepository.delete(category);
	}

	
	public void generateCategoriesForUser(UserAccount user) {
		List<Category> categories = Arrays.asList(
				new Category(user, "Salary", "income"),
				new Category(user, "Bonus", "income"),
				new Category(user, "Dividends", "income"),
				new Category(user, "Gift", "income"),
				new Category(user, "Utilities", "expense"),
				new Category(user, "Bills", "expense"),
				new Category(user, "Gas", "expense"),
				new Category(user, "Groceries", "expense")
				); 

		categoryRepository.saveAll(categories);
	}
}
