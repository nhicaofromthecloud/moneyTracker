package com.example.moneyM.controller;

import com.example.moneyM.model.UserAccount;
import com.example.moneyM.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/userAccounts")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    // Create a new user account
    @PostMapping
    public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {
        UserAccount savedUserAccount = userAccountService.createUserAccount(userAccount);
        return new ResponseEntity<>(savedUserAccount, HttpStatus.CREATED);
    }

    // Get all user accounts
    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllUserAccounts() {
        List<UserAccount> userAccounts = userAccountService.getAllUserAccounts();
        return ResponseEntity.ok(userAccounts);
    }

    // Get a single user account by id
    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserAccountById(@PathVariable Long id) {
        UserAccount userAccount = userAccountService.getUserAccountById(id);
        return ResponseEntity.ok(userAccount);
    }

    // Update a user account
    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> updateUserAccount(@PathVariable Long id, @RequestBody UserAccount userDetails) {
        UserAccount updatedUserAccount = userAccountService.updateUserAccount(id, userDetails);
        return ResponseEntity.ok(updatedUserAccount);
    }

    // Delete a user account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
        userAccountService.deleteUserAccount(id);
        return ResponseEntity.noContent().build();
    }
}
