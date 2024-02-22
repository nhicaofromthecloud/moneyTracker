package com.example.moneyM.service;

import com.example.moneyM.model.Goal;
import com.example.moneyM.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    // Create a new goal
    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    // Get all goals
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    // Get a goal by ID
    public Goal getGoalById(Long id) {
        Optional<Goal> goal = goalRepository.findById(id);
        if (goal.isPresent()) {
            return goal.get();
        } else {
            throw new RuntimeException("Goal not found for id :: " + id);
        }
    }

    // Update a goal
    public Goal updateGoal(Long id, Goal goalDetails) {
        Goal goal = getGoalById(id); // Reuse getGoalById to ensure the goal exists

        goal.setName(goalDetails.getName());
        goal.setTargetAmount(goalDetails.getTargetAmount());
        goal.setCurrentAmount(goalDetails.getCurrentAmount());
        goal.setStartDate(goalDetails.getStartDate());
        goal.setTargetDate(goalDetails.getTargetDate());

        return goalRepository.save(goal);
    }

    // Delete a goal
    public void deleteGoal(Long id) {
        Goal goal = getGoalById(id); // Reuse getGoalById to ensure the goal exists
        goalRepository.delete(goal);
    }
}
