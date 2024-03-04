package com.example.moneyM.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyM.dto.WalletDto;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.model.Wallet;
import com.example.moneyM.repository.UserAccountRepository;
import com.example.moneyM.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserAccountRepository userAccountRepository;


	public WalletService(WalletRepository walletRepository, UserAccountRepository userAccountRepository) {
		this.walletRepository = walletRepository;
		this.userAccountRepository = userAccountRepository;
	}

	public WalletDto createWallet(WalletDto walletDto) {
		UserAccount userAccount = userAccountRepository.findById(walletDto.getUserId())
				.orElseThrow(() -> new RuntimeException("UserAccount not found"));

		//convert DTO to Entity
		Wallet walletRequest = modelMapper.map(walletDto, Wallet.class);

		// set UserAccount for wallet entity
		walletRequest.setUserAccount(userAccount);

		Wallet wallet = walletRepository.save(walletRequest);

		// convert Entity to DTO
		WalletDto walletResponse = modelMapper.map(wallet, WalletDto.class);


		return walletResponse;
	}

	public List<WalletDto> getAllWallets() {
		List<Wallet> walletList = walletRepository.findAll();
		return walletList
				.stream()
				.map(wallet -> modelMapper.map(wallet, WalletDto.class))
				.collect(Collectors.toList());
	}


	public WalletDto getWalletById(Long id) {
		Wallet wallet = walletRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Wallet not found"));
		
		// convert Entity to DTO
		WalletDto walletDto = modelMapper.map(wallet, WalletDto.class);
		
		return walletDto;
		
	}

	public WalletDto updateWallet(Long id, WalletDto walletDto) {

		Wallet wallet = walletRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Wallet not found"));

		// update name and current amount only
		wallet.setName(walletDto.getName());
		wallet.setCurrentAmount(walletDto.getCurrentAmount());

		// save the updated wallet entity
		Wallet updatedWallet = walletRepository.save(wallet);

		// convert the updated wallet entity to DTO
		WalletDto updatedWalletDto = modelMapper.map(updatedWallet, WalletDto.class);

		return updatedWalletDto;
	}

	public void deleteWallet(Long id) {
		walletRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("Wallet not found for id: " + id));

		walletRepository.deleteById(id);
	}
}
