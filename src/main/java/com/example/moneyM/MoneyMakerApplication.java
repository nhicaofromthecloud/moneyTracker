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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
            
            // create basic wallet for each user
            Wallet user1BasicWallet = new Wallet(user1, "Default Wallet", WalletType.BASIC, 0.00);
            Wallet user2BasicWallet = new Wallet(user2, "Default Wallet", WalletType.BASIC, 0.00);
            Wallet user3BasicWallet = new Wallet(user3, "Default Wallet", WalletType.BASIC, 0.00);
           
            walletRepo.save(user1BasicWallet);
            walletRepo.save(user2BasicWallet);
            walletRepo.save(user3BasicWallet);
            

            
            // Add sample goals for each user
            goalRepo.save(new Goal(user1, "Europe Trip", 
            			  5000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(12)));
            goalRepo.save(new Goal(user1, "Emergency Fund", 
            		      10000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(18)));

            goalRepo.save(new Goal(user2, "Home Down Payment", 
            		      20000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(2)));
            goalRepo.save(new Goal(user2, "Car Replacement", 
            		      15000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(3)));

            goalRepo.save(new Goal(user3, "Retirement Savings", 
            		      30000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(5)));
            goalRepo.save(new Goal(user3, "College Fund", 
            		      25000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(10)));
            

            Category salary1 = new Category(user1, "Salary", "income");
            Category salary2 = new Category(user2, "Salary", "income");
            Category salary3 = new Category(user3, "Salary", "income");

            Category bonus1 = new Category(user1, "Bonus", "income");
            Category bonus2 = new Category(user2, "Bonus", "income");
            Category bonus3 = new Category(user3, "Bonus", "income");
            
            Category dividends1 = new Category(user1, "Dividends", "income");
            Category dividends2 = new Category(user2, "Dividends", "income");
            Category dividends3 = new Category(user3, "Dividends", "income");
            
            Category gift1 = new Category(user1, "Gift", "income");
            Category gift2 = new Category(user2, "Gift", "income");
            Category gift3 = new Category(user3, "Gift", "income");
            
            Category utilities1 = new Category(user1, "Utilities", "expense");
            Category utilities2 = new Category(user2, "Utilities", "expense");
            Category utilities3 = new Category(user3, "Utilities", "expense");
            
            Category bills1 = new Category(user1, "Bills", "expense");
            Category bills2 = new Category(user2, "Bills", "expense");
            Category bills3 = new Category(user3, "Bills", "expense");
            
            Category gas1 = new Category(user1, "Gas", "expense");
            Category gas2 = new Category(user2, "Gas", "expense");
            Category gas3 = new Category(user3, "Gas", "expense");
            
            Category groceries1 = new Category(user1, "Groceries", "expense");
            Category groceries2 = new Category(user2, "Groceries", "expense");
            Category groceries3 = new Category(user3, "Groceries", "expense");

            categoryRepo.save(salary1);
            categoryRepo.save(salary2);
            categoryRepo.save(salary3);
            categoryRepo.save(bonus1);
            categoryRepo.save(bonus2);
            categoryRepo.save(bonus3);
            categoryRepo.save(dividends1);
            categoryRepo.save(dividends2);
            categoryRepo.save(dividends3);
            categoryRepo.save(gift1);
            categoryRepo.save(gift2);
            categoryRepo.save(gift3);
            categoryRepo.save(utilities1);
            categoryRepo.save(utilities2);
            categoryRepo.save(utilities3);
            categoryRepo.save(bills1);
            categoryRepo.save(bills2);
            categoryRepo.save(bills3);
            categoryRepo.save(gas1);
            categoryRepo.save(gas2);
            categoryRepo.save(gas3);
            categoryRepo.save(groceries1);
            categoryRepo.save(groceries2);
            
            //Add budget
            budgetRepo.save(new Budget(user1, salary1, 2000.50, "year"));
            budgetRepo.save(new Budget(user1, gas1, 200.0, "month"));
            budgetRepo.save(new Budget(user2, gas2, 300.0, "month"));
            budgetRepo.save(new Budget(user2, bills2, 200.55, "month"));
            budgetRepo.save(new Budget(user2, utilities2, 900.33, "week"));

            
            // Create sample transactions for each user
            Transaction transaction1 = new Transaction(user1, user1BasicWallet, salary1, 
            										   1000.0, LocalDate.now(), "Salary");
            Transaction transaction2 = new Transaction(user1, user1BasicWallet, groceries1, 
            		                                   50.0, LocalDate.now(), "Groceries");
               
            Transaction transaction3 = new Transaction(user2, user2BasicWallet, groceries2, 
            		                                   200.0, LocalDate.now(), "Waltermart groceries");
            Transaction transaction4 = new Transaction(user2, user2BasicWallet, salary2, 
            		                                   500.0, LocalDate.now(), "Part-time QA");
               
            Transaction transaction5 = new Transaction(user3, user3BasicWallet, utilities3, 
            		                                   1200.0, LocalDate.now(), "Rent");
            Transaction transaction6 = new Transaction(user3, user3BasicWallet, salary3, 
            		                                   2000.0, LocalDate.now(), "Online Selling income");
               
               
            // Add transactions to repo
            List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3, 
               												transaction4, transaction5, transaction6);
            transactionRepo.saveAll(transactions);
        };
    }
}
