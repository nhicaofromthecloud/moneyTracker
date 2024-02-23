package com.example.moneyM.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.model.Category;
import com.example.moneyM.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	//Create new category
	public Category createCategory(Category category) {
		Optional<Category> existingCategory = categoryRepository.findCategoryByName(category.getName());
//		Optional<Category> categoryByType = categoryRepository.findCategoryByType(category.getType());
		
		if(existingCategory.isPresent() && existingCategory.get().getType().equals(category.getType())) {
			throw new RuntimeException("Category and Type already exists");
		}
		
		return categoryRepository.save(category);
	}
	
	//Get all category
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	//Get a category by ID
	public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new RuntimeException("Category not found for id :: " + id));
    }

    // Update a category
    public Category updateCategory(Long id, Category categoryDetails) {
    	
        Category category = getCategoryById(id); // Reuse getCategoryById to ensure the category exists

        Optional<Category> existingCategory = categoryRepository.findCategoryByName(categoryDetails.getName());
        
        if (existingCategory.isPresent() && existingCategory.get().getType().equals(categoryDetails.getType())) {
        	throw new RuntimeException("Category and Type already exists");
        }
    	
        category.setName(categoryDetails.getName());
        category.setType(categoryDetails.getType());

        return categoryRepository.save(category);
    }

    // Delete a category
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id); // Reuse getCategoryById to ensure the category exists
        categoryRepository.delete(category);
    }


}
