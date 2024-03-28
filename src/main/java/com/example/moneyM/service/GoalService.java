package com.example.moneyM.service;

import com.example.moneyM.dto.GoalDto;
import com.example.moneyM.dto.GoalResponse;
import com.example.moneyM.dto.WalletDto;
import com.example.moneyM.model.Goal;
import com.example.moneyM.model.UserAccount;
import com.example.moneyM.model.Wallet;
import com.example.moneyM.model.WalletType;
import com.example.moneyM.repository.GoalRepository;
import com.example.moneyM.repository.UserAccountRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {

	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private WalletService walletService;

	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private UserAccountRepository userAccountRepository;



	public GoalService(GoalRepository goalRepository, UserAccountRepository userAccountRepository) {
		this.goalRepository = goalRepository;
		this.userAccountRepository = userAccountRepository;
	}

	// Create a new goal
	public GoalDto createGoal(GoalDto goalDto) {
		UserAccount userAccount = userAccountRepository.findById(goalDto.getUserId())
				.orElseThrow(() -> new RuntimeException("UserAccount not found"));
		
		// create wallet for the new goal
	    WalletDto walletDto = new WalletDto();
	    walletDto.setUserId(goalDto.getUserId());
	    walletDto.setName(goalDto.getName());
	    walletDto.setType(WalletType.GOAL);
	    walletDto.setCurrentAmount(goalDto.getCurrentAmount());
	    WalletDto createdWallet = walletService.createWallet(walletDto);

		//convert DTO to Entity
		Goal goalRequest = modelMapper.map(goalDto, Goal.class);
		Wallet goalWallet = modelMapper.map(createdWallet, Wallet.class);

		// set UserAccount for Goal entity
		goalRequest.setUserAccount(userAccount);
		goalRequest.setWallet(goalWallet);

		// convert dto String dates to LocalDate
		LocalDate startDate = LocalDate.parse(goalDto.getStartDate());
		goalRequest.setStartDate(startDate);

		LocalDate targetDate = LocalDate.parse(goalDto.getTargetDate());
		goalRequest.setTargetDate(targetDate);

		Goal goal = goalRepository.save(goalRequest);

		// convert Entity to DTO
		GoalDto goalResponse = modelMapper.map(goal, GoalDto.class);

		return goalResponse;
	}

	// Get all goals
	public List<GoalResponse> getAllGoals(Long userId) {
		List<Goal> goalList = new ArrayList<Goal>();
		if(userId != null) {
			goalList = goalRepository.findByUserAccountUserId(userId);
		} else {
			goalList = goalRepository.findAll();
		}	
				
		return goalList
				.stream()
				.map(goal -> modelMapper.map(goal,  GoalResponse.class))
				.collect(Collectors.toList());
	}

	// Get a goal by ID
	public GoalResponse getGoalById(Long id) {
		Goal goal = goalRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(("Goal not found: " + id)));

		// convert Entity to DTO
		GoalResponse goalResponse = modelMapper.map(goal, GoalResponse.class);

		return goalResponse;

	}

	// Update a goal
	public GoalDto updateGoal(Long id, GoalDto goalDto) {
		Goal goal = goalRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(("Goal not found: " + id)));

		goal.setName(goalDto.getName());
		goal.setTargetAmount(goalDto.getTargetAmount());
		goal.setCurrentAmount(goalDto.getCurrentAmount());
		goal.setStartDate(LocalDate.parse(goalDto.getStartDate()));
		goal.setTargetDate(LocalDate.parse(goalDto.getTargetDate()));

		Goal updatedGoal = goalRepository.save(goal);

		// convert Entity to DTO
		GoalDto updateGoalDto = modelMapper.map(updatedGoal, GoalDto.class);

		return updateGoalDto;
	}

	// Delete a goal
	public void deleteGoal(Long id) {
		goalRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Goal not found for id: " + id));

		goalRepository.deleteById(id);
	}
}
