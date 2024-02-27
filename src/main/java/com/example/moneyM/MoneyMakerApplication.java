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
            
            Wallet user1BasicWallet = new Wallet(user1, "Default Wallet", WalletType.BASIC, 0.00);
            Wallet user2BasicWallet = new Wallet(user2, "Default Wallet", WalletType.BASIC, 0.00);
            Wallet user3BasicWallet = new Wallet(user3, "Default Wallet", WalletType.BASIC, 0.00);
            
            
            // create basic wallet for each user
            walletRepo.save(user1BasicWallet);
            walletRepo.save(user2BasicWallet);
            walletRepo.save(user3BasicWallet);
            

            // Add sample goals for each user
            goalRepo.save(new Goal(user1, "Europe Trip", 5000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(12)));
            goalRepo.save(new Goal(user1, "Emergency Fund", 10000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(18)));

            goalRepo.save(new Goal(user2, "Home Down Payment", 20000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(2)));
            goalRepo.save(new Goal(user2, "Car Replacement", 15000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(3)));

            goalRepo.save(new Goal(user3, "Retirement Savings", 30000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(5)));
            goalRepo.save(new Goal(user3, "College Fund", 25000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(10)));
            

            Category salary = new Category("Salary", "income");
            Category bonus = new Category("Bonus", "income");
            Category dividends = new Category("Dividends", "income");
            Category gift = new Category("Gift", "income");
            Category utilities = new Category("Utilities", "expense");
            Category bills = new Category("Bills", "expense");
            Category gas = new Category("Gas", "expense");
            Category groceries = new Category("Groceries", "expense");

            categoryRepo.save(salary);
            categoryRepo.save(bonus);
            categoryRepo.save(dividends);
            categoryRepo.save(gift);
            categoryRepo.save(utilities);
            categoryRepo.save(bills);
            categoryRepo.save(gas);
            categoryRepo.save(groceries);
            
            //Add budget
            budgetRepo.save(new Budget(user1, salary, 2000.50, "year"));
            budgetRepo.save(new Budget(user1, gas, 200.0, "month"));
            budgetRepo.save(new Budget(user2, gas, 300.0, "month"));
            budgetRepo.save(new Budget(user2, bills, 200.55, "month"));
            budgetRepo.save(new Budget(user2, utilities, 900.33, "week"));

            
            // Create sample transactions for each user
            Transaction transaction1 = new Transaction(user1, user1BasicWallet, salary, "income", 1000.0, LocalDate.now(), "Salary");
            Transaction transaction2 = new Transaction(user1, user1BasicWallet, groceries, "expense", 50.0, LocalDate.now(), "Groceries");
               
            Transaction transaction3 = new Transaction(user2, user2BasicWallet, groceries, "expense", 200.0, LocalDate.now(), "Waltermart groceries");
            Transaction transaction4 = new Transaction(user2, user2BasicWallet, salary, "income", 500.0, LocalDate.now(), "Part-time QA");
               
            Transaction transaction5 = new Transaction(user2, user3BasicWallet, utilities, "expense", 1200.0, LocalDate.now(), "Rent");
            Transaction transaction6 = new Transaction(user2, user3BasicWallet, salary, "income", 2000.0, LocalDate.now(), "Online Selling income");
               
               
            // Add transactions to repo
            List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3, 
               												transaction4, transaction5, transaction6);
            transactionRepo.saveAll(transactions);
        };
    }
}
