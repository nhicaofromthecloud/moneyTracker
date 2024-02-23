package com.example.moneyM;

import com.example.moneyM.model.Category;
import com.example.moneyM.model.Goal;
import com.example.moneyM.model.Transaction;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.CategoryRepository;
import com.example.moneyM.repository.GoalRepository;
import com.example.moneyM.repository.TransactionRepository;
import com.example.moneyM.repository.UserAccountRepository;
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
    								  TransactionRepository transactionRepo) {
        return args -> {
            // Create sample user accounts
            UserAccount user1 = new UserAccount("User1", "user1@example.com", "password1");
            UserAccount user2 = new UserAccount("User2", "user2@example.com", "password2");
            UserAccount user3 = new UserAccount("User3", "user3@example.com", "password3");

            userAccountRepo.save(user1);
            userAccountRepo.save(user2);
            userAccountRepo.save(user3);

            // Add sample goals for each user
            goalRepo.save(new Goal(user1, "Europe Trip", 5000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(12)));
            goalRepo.save(new Goal(user1, "Emergency Fund", 10000.00, 0.00, LocalDate.now(), LocalDate.now().plusMonths(18)));

            goalRepo.save(new Goal(user2, "Home Down Payment", 20000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(2)));
            goalRepo.save(new Goal(user2, "Car Replacement", 15000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(3)));

            goalRepo.save(new Goal(user3, "Retirement Savings", 30000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(5)));
            goalRepo.save(new Goal(user3, "College Fund", 25000.00, 0.00, LocalDate.now(), LocalDate.now().plusYears(10)));
            

            // Add category
            Category category1 = new Category("Salary", "income");
            Category category2 = new Category("Bonus", "income");
            Category category3 = new Category("Dividends", "income");
            Category category4 = new Category("Gift", "income");
            Category category5 = new Category("Utilities", "expense");
            Category category6 = new Category("Bills", "expense");
            Category category7 = new Category("Gas", "expense");
            Category category8 = new Category("Groceries", "expense");
           
            categoryRepo.save(category1);
            categoryRepo.save(category2);
            categoryRepo.save(category3);
            categoryRepo.save(category4);
            categoryRepo.save(category5);
            categoryRepo.save(category6);
            categoryRepo.save(category7);
            categoryRepo.save(category8);
          
           
         // Create sample transactions for each user
            Transaction transaction1 = new Transaction(user1, category1, "Income", 1000.0, LocalDate.now(), "Salary");
            Transaction transaction2 = new Transaction(user1, category8, "Expense", 50.0, LocalDate.now(), "Groceries");
            
            Transaction transaction3 = new Transaction(user2, category6, "Expense", 200.0, LocalDate.now(), "Dinner with friends");
            Transaction transaction4 = new Transaction(user2, category2, "Income", 500.0, LocalDate.now(), "Part-time QA");
            
            Transaction transaction5 = new Transaction(user2, category6, "Expense", 1200.0, LocalDate.now(), "Rent");
            Transaction transaction6 = new Transaction(user2, category2, "Income", 2000.0, LocalDate.now(), "Online Selling Inc");
            
            
            // Add transactions to repo
            List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3, 
            												transaction4, transaction5, transaction6);
            transactionRepo.saveAll(transactions);
         
           
            

        };
    }
}
