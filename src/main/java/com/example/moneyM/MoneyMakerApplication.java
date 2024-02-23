package com.example.moneyM;

import com.example.moneyM.model.Category;
import com.example.moneyM.model.Goal;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.repository.CategoryRepository;
import com.example.moneyM.repository.GoalRepository;
import com.example.moneyM.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MoneyMakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyMakerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(UserAccountRepository userAccountRepo, GoalRepository goalRepo, CategoryRepository categoryRepo) {
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
            categoryRepo.save(new Category("Salary", "income"));
            categoryRepo.save(new Category("Bonus", "income"));
            categoryRepo.save(new Category("Dividends", "income"));
            categoryRepo.save(new Category("Gift", "income"));
            categoryRepo.save(new Category("Utilities", "expense"));
            categoryRepo.save(new Category("Bills", "expense"));
            categoryRepo.save(new Category("Gas", "expense"));
        };
    }
}
