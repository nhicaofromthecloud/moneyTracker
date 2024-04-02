package com.example.moneyM;

import com.example.moneyM.model.Budget;
import com.example.moneyM.model.Category;
import com.example.moneyM.model.Goal;
import com.example.moneyM.model.Transaction;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.model.Wallet;
import com.example.moneyM.model.WalletType;
import com.example.moneyM.repository.BudgetRepository;
import com.example.moneyM.repository.CategoryRepository;
import com.example.moneyM.repository.GoalRepository;
import com.example.moneyM.repository.TransactionRepository;
import com.example.moneyM.repository.UserAccountRepository;
import com.example.moneyM.repository.WalletRepository;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class MoneyMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyMakerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	@Bean
	public CommandLineRunner demoData(UserAccountRepository userAccountRepo,
			GoalRepository goalRepo,
			CategoryRepository categoryRepo,
			BudgetRepository budgetRepo,
			TransactionRepository transactionRepo,
			WalletRepository walletRepo) {

		return args -> {
			// Create sample user accounts
			UserAccount user1 = new UserAccount("User1", "user1@example.com", "password1");
			UserAccount user2 = new UserAccount("User2", "user2@example.com", "password2");
			UserAccount user3 = new UserAccount("User3", "user3@example.com", "password3");

			userAccountRepo.save(user1);
			userAccountRepo.save(user2);
			userAccountRepo.save(user3);


			// Add sample goals for each user
			Wallet user1GoalWallet1 = new Wallet(user1, "Goal Wallet 1", WalletType.GOAL, 0.00);
			Wallet user1GoalWallet2 = new Wallet(user1, "Goal Wallet 2", WalletType.GOAL, 0.00);
			walletRepo.save(user1GoalWallet1);
			walletRepo.save(user1GoalWallet2);
			
			goalRepo.save(new Goal(user1, user1GoalWallet1, "Europe Trip", 5000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(12)));
			goalRepo.save(new Goal(user1, user1GoalWallet2, "Emergency Fund", 
					10000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(18)));

			Wallet user2GoalWallet1 = new Wallet(user2, "Goal Wallet 1", WalletType.GOAL, 0.00);
			Wallet user2GoalWallet2 = new Wallet(user2, "Goal Wallet 2", WalletType.GOAL, 0.00);
			walletRepo.save(user2GoalWallet1);
			walletRepo.save(user2GoalWallet2);
			
			goalRepo.save(new Goal(user2, user2GoalWallet1, "Home Down Payment", 
					20000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(2)));
			goalRepo.save(new Goal(user2, user2GoalWallet2, "Car Replacement", 
					15000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(3)));

			Wallet user3GoalWallet1 = new Wallet(user3, "Goal Wallet 1", WalletType.GOAL, 0.00);
			Wallet user3GoalWallet2 = new Wallet(user3, "Goal Wallet 2", WalletType.GOAL, 0.00);
			walletRepo.save(user3GoalWallet1);
			walletRepo.save(user3GoalWallet2);
			
			goalRepo.save(new Goal(user3, user3GoalWallet1, "Retirement Savings", 
					30000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(5)));
			goalRepo.save(new Goal(user3, user3GoalWallet2, "College Fund", 
					25000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(10)));

			
			// create categories for each user
			generateCategoriesForUser(user1, categoryRepo);
			generateCategoriesForUser(user2, categoryRepo);
			generateCategoriesForUser(user3, categoryRepo);
			
			// create budgets for each user
			generateBudgetsForUser(user1, budgetRepo, categoryRepo);
			generateBudgetsForUser(user2, budgetRepo, categoryRepo);
			generateBudgetsForUser(user3, budgetRepo, categoryRepo);

			// Create sample transactions for each user
			generateTransactionsForUser(user1, transactionRepo, walletRepo, categoryRepo);
			generateTransactionsForUser(user2, transactionRepo, walletRepo, categoryRepo);
			generateTransactionsForUser(user3, transactionRepo, walletRepo, categoryRepo);


		};
	}

	public void generateCategoriesForUser(UserAccount user, CategoryRepository categoryRepo) {
		List<Category> categories = Arrays.asList(
				new Category(user, "Salary", "income"),
				new Category(user, "Bonus", "income"),
				new Category(user, "Dividends", "income"),
				new Category(user, "Gift", "income"),
				new Category(user, "Utilities", "expense"),
				new Category(user, "Bills", "expense"),
				new Category(user, "Gas", "expense"),
				new Category(user, "Groceries", "expense")
				); 

		categoryRepo.saveAll(categories);
	}
	
	public void generateBudgetsForUser(UserAccount user, BudgetRepository budgetRepo, CategoryRepository categoryRepo) {
		Optional<Category> gasData = categoryRepo.findCategoryByNameAndUserAccountUserId("Gas", user.getUserId());
		Category gas = gasData.get();
		
		Optional<Category> billsData = categoryRepo.findCategoryByNameAndUserAccountUserId("Bills", user.getUserId());
		Category bills = billsData.get();
		
		Optional<Category> groceriesData = categoryRepo.findCategoryByNameAndUserAccountUserId("Groceries", user.getUserId());
		Category groceries = groceriesData.get();
		
		
		List<Budget> budgets = Arrays.asList(
				new Budget(user, gas, 200.0, "month"),
				new Budget(user, bills, 200.55, "month"),
				new Budget(user, groceries, 500.00, "month")
				); 

		budgetRepo.saveAll(budgets);
	}
	
	public void generateTransactionsForUser(UserAccount user, 
			TransactionRepository transactionRepo, 
			WalletRepository walletRepo, 
			CategoryRepository categoryRepo) {
		
		Optional<Category> salaryData = categoryRepo.findCategoryByNameAndUserAccountUserId("Salary", user.getUserId());
		Category salary = salaryData.get();
		
		Optional<Category> gasData = categoryRepo.findCategoryByNameAndUserAccountUserId("Gas", user.getUserId());
		Category gas = gasData.get();
		
		Optional<Category> billsData = categoryRepo.findCategoryByNameAndUserAccountUserId("Bills", user.getUserId());
		Category bills = billsData.get();
		
		Optional<Category> groceriesData = categoryRepo.findCategoryByNameAndUserAccountUserId("Groceries", user.getUserId());
		Category groceries = groceriesData.get();

		
		Wallet basicWallet = new Wallet(user, "Default Wallet", WalletType.BASIC, 0.00);
		walletRepo.save(basicWallet);
	
		
		List<Transaction> transactions = Arrays.asList(
				new Transaction(user, basicWallet, salary, 1000.0, LocalDate.of(2024,3,01), "March Salary 1"),
				new Transaction(user, basicWallet, groceries, 50.0, LocalDate.of(2024, 3, 10), "Walmart"),
				new Transaction(user, basicWallet, bills, 30.0, LocalDate.of(2024, 3, 15), "Hydro"),
				new Transaction(user, basicWallet, salary, 1000.0, LocalDate.of(2024,3,28), "March Salary 2"),
				new Transaction(user, basicWallet, groceries, 100.0, LocalDate.of(2024, 3, 30), "Costco"),
				new Transaction(user, basicWallet, gas, 60.0, LocalDate.now(), "Chevron")
				); 

		transactionRepo.saveAll(transactions);
	}

}
