package com.example.moneyM.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.response.MessageResponse;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.UserAccountRepository;
import com.example.moneyM.request.UserLoginRequest;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	UserAccountRepository userRepository;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
		
		try {
			String user = loginRequest.getUsername();
			Optional<UserAccount> userData = userRepository.findByUsername(user);
			if (userData.isPresent()) {
				String password = userData.get().getPassword();
				if (password.equals(loginRequest.getPassword())) {
					MessageResponse msg = new MessageResponse("Welcome " + user);
					return new ResponseEntity<>(msg, HttpStatus.OK);
				}
				MessageResponse msg = new MessageResponse("Incorrect password");
				return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
			}
			MessageResponse msg = new MessageResponse("Incorrect username");
			return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			MessageResponse msg = new MessageResponse("Server Error");
			return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
}
