package com.example.moneyM.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.model.Wallet;
import com.example.moneyM.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepository;

	public WalletService(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}
	
	public Wallet createWallet(Wallet wallet) {
		return walletRepository.save(wallet);
	}
	
	public List<Wallet> getAllWallets() {
		return walletRepository.findAll();
	}
	

    public Wallet getWalletById(Long id) {
    	Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        } else {
            throw new RuntimeException("Wallet not found for id: " + id);
        }
    }
	
	public Wallet updateWallet(Long id, Wallet walletData) {
		
		Wallet wallet = getWalletById(id);
		
		// update name and current amount only
		wallet.setName(walletData.getName());
		wallet.setCurrentAmount(walletData.getCurrentAmount());
		
		return walletRepository.save(wallet);
	}
	
	public void deleteWallet(Long id) {
		walletRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Wallet not found for id: " + id));
		
		walletRepository.deleteById(id);
	}
}
