package com.example.moneyM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moneyM.model.Wallet;
import com.example.moneyM.model.WalletType;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
	
	List<Wallet> findByType(WalletType type); 
	List<Wallet> findByUserAccountUserId(Long userId);
	
}
