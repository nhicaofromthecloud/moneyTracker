package com.example.moneyM.service;

import java.util.ArrayList;
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

	    Optional<Category> existingCategory = categoryRepository.findCategoryByNameAndUserAccountUserId(categoryDto.getName(), userAccount.getUserId());

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
//        Optional<Category> category = categoryRepository.findById(id);
//        return category.orElseThrow(() -> new RuntimeException("Category not found for id :: " + id));
		
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
	    
	    category.setName(categoryDto.getName());
	    category.setType(categoryDto.getType());
	    
	    //sae category entitiy
	    Category updatedCategory = categoryRepository.save(category);
	    
	    //convert the updated category to entity dto
	    CategoryDto updatedCategoryDto = modelMapper.map(updatedCategory, CategoryDto.class);
	    
	    return updatedCategoryDto;
	}
	
    // Update a category (old)
//    public Category updateCategory(Long id, Category categoryDetails) {
//    	
//        Category category = getCategoryById(id); // Reuse getCategoryById to ensure the category exists
//
//        Optional<Category> existingCategory = categoryRepository.findCategoryByName(categoryDetails.getName());
//        
//        if (existingCategory.isPresent() && existingCategory.get().getType().equals(categoryDetails.getType())) {
//        	throw new RuntimeException("Category and Type already exists");
//        }
//    	
//        category.setName(categoryDetails.getName());
//        category.setType(categoryDetails.getType());
//
//        return categoryRepository.save(category);
//    }

    // Delete a category
    public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found for id :: " + id));
        categoryRepository.delete(category);
    }


}
