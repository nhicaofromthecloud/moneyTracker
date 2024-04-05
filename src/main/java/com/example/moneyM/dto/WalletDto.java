package com.example.moneyM.dto;

import com.example.moneyM.model.WalletType;

public class WalletDto {
	
	private Long id;
	private Long userId;
	private String name;
	private WalletType type;
	private Long walletId;
	private Double currentAmount;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public WalletType getType() {
		return type;
	}
	
	public void setType(WalletType type) {
		this.type = type;
	}
	
	public Double getCurrentAmount() {
		return currentAmount;
	}
	
	public void setCurrentAmount(Double currentAmount) {
		this.currentAmount = currentAmount;
	}	
}
