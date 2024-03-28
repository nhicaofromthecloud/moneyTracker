package com.example.moneyM.service;

import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {
	
	@Autowired
	private CategoryService categoryService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    // Create a new user account
    public UserAccount createUserAccount(UserAccount userAccount) {
    	UserAccount user =  userAccountRepository.save(userAccount);
    	// add default categories when user created
        categoryService.generateCategoriesForUser(user);
        return user;
    }

    // Get all user accounts
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    // Get a user account by ID
    public UserAccount getUserAccountById(Long id) {
        Optional<UserAccount> userAccount = userAccountRepository.findById(id);
        return userAccount.orElseThrow(() -> new RuntimeException("User account not found for id :: " + id));
    }

    // Update a user account
    public UserAccount updateUserAccount(Long id, UserAccount userAccountDetails) {
        UserAccount userAccount = getUserAccountById(id); // Reuse getUserAccountById to ensure the user account exists

        userAccount.setUsername(userAccountDetails.getUsername());
        userAccount.setEmail(userAccountDetails.getEmail());
        userAccount.setPassword(userAccountDetails.getPassword()); // Consider encryption for passwords

        return userAccountRepository.save(userAccount);
    }

    // Delete a user account
    public void deleteUserAccount(Long id) {
        UserAccount userAccount = getUserAccountById(id); // Reuse getUserAccountById to ensure the user account exists
        userAccountRepository.delete(userAccount);
    }
}
