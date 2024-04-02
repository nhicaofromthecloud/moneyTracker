package com.example.moneyM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moneyM.dto.WalletDto;
import com.example.moneyM.model.Wallet;
import com.example.moneyM.service.WalletService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/wallets")
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	@PostMapping
	public ResponseEntity<WalletDto> createWallet(@RequestBody WalletDto wallet) {
		WalletDto newWallet = walletService.createWallet(wallet);
		return new ResponseEntity<>(newWallet, HttpStatus.CREATED);
	};
	
	@GetMapping
	public ResponseEntity<List<WalletDto>> getAllWallets() {
		List<WalletDto> wallets = walletService.getAllWallets();
		return ResponseEntity.ok(wallets);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<WalletDto> getWalletById(@PathVariable Long id) {
		WalletDto wallet = walletService.getWalletById(id);
		return ResponseEntity.ok(wallet); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<WalletDto> updateWallet(@PathVariable Long id,
											   @RequestBody WalletDto wallet) {
		
		WalletDto updatedWallet = walletService.updateWallet(id, wallet);
		return ResponseEntity.ok(updatedWallet); 
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {
		 walletService.deleteWallet(id);
		 return ResponseEntity.noContent().build();
	}
	
}
