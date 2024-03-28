package com.example.moneyM.controller;

import com.example.moneyM.dto.GoalDto;
import com.example.moneyM.dto.GoalResponse;
import com.example.moneyM.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    // Create a new goal
    @PostMapping
    public ResponseEntity<GoalDto> createGoal(@RequestBody GoalDto goalDto) {
    	GoalDto savedGoal = goalService.createGoal(goalDto);
        return new ResponseEntity<>(savedGoal, HttpStatus.CREATED);
    }

    // Get all goals
    @GetMapping
    public ResponseEntity<List<GoalResponse>> getAllGoals(@RequestParam(required = false) Long userId) {
        List<GoalResponse> goals = goalService.getAllGoals(userId);
        return ResponseEntity.ok(goals);
    }

    // Get a single goal by id
    @GetMapping("/{id}")
    public ResponseEntity<GoalResponse> getGoalById(@PathVariable Long id) {
    	GoalResponse goal = goalService.getGoalById(id);
        return ResponseEntity.ok(goal);
    }

    // Update a goal
    @PutMapping("/{id}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable Long id, @RequestBody GoalDto goalDto) {
    	GoalDto updatedGoal = goalService.updateGoal(id, goalDto);
        return ResponseEntity.ok(updatedGoal);
    }

    // Delete a goal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
