package com.example.moneyM.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wallets")
public class Wallet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private UserAccount userAccount;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "type")
    private WalletType type;
	
	@Column(name = "currentAmount")
	private Double currentAmount;

	public Wallet() {
	}

	public Wallet(UserAccount userAccount, String name, WalletType type, Double currentAmount) {
		this.userAccount = userAccount;
		this.name = name;
		this.type = type;
		this.currentAmount = currentAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
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
