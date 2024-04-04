package com.example.moneyM.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.UserAccountRepository;
import com.example.moneyM.request.UserLoginRequest;
import com.example.moneyM.response.MessageResponse;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	UserAccountRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
		try {
			String userInfo = loginRequest.getUsername();
			Optional<UserAccount> userData;

			if (userInfo.contains("@")) {
				userData = userRepository.findByEmail(userInfo);
			} else {
				userData = userRepository.findByUsername(userInfo);
			}

			if (userData.isPresent()) {
				String password = userData.get().getPassword();
				if (password.equals(loginRequest.getPassword())) {
					UserAccount user = userData.get();
					user.setPassword(null); // Remove the password for security reasons

					// Return the UserAccount object, excluding the password
					return new ResponseEntity<>(user, HttpStatus.OK);
				}
				return new ResponseEntity<>(new MessageResponse("Incorrect password"), HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(new MessageResponse("Incorrect username"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}