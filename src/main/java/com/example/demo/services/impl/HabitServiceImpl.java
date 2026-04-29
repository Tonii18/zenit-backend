package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Habit;
import com.example.demo.entities.User;
import com.example.demo.models.HabitRequestDTO;
import com.example.demo.models.HabitResponseDTO;
import com.example.demo.repository.HabitRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.HabitService;

@Service
public class HabitServiceImpl implements HabitService {

	@Autowired
	@Qualifier("habitRepository")
	private HabitRepository habitRepo;

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepo;

	@Override
	public HabitResponseDTO createHabit(HabitRequestDTO request, Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		Habit habit = new Habit();
		
		habit.setUser(user);
		habit.setName(request.getName());
		habit.setCompleted(false);
		
		Habit saved = habitRepo.save(habit);
		
		return toDto(saved);
	}

	@Override
	public List<HabitResponseDTO> getHabits(Long userId) {
		// TODO Auto-generated method stub
		List<HabitResponseDTO> results = new ArrayList<>();
		
		List<Habit> habits = habitRepo.findByUserId(userId);
		
		for(Habit habit: habits) {
			results.add(toDto(habit));
		}
		
		return results;
	}

	@Override
	public int deleteHabit(Long id) {
		// TODO Auto-generated method stub
		if(!habitRepo.existsById(id)) {
			throw new IllegalArgumentException("This habit does not exist, so it cannot be deleted");
		}
		habitRepo.deleteById(id);
		
		return 0;
	}
	
	@Override
	public HabitResponseDTO checkHabit(Long id) {
		// TODO Auto-generated method stub
		Habit habit = habitRepo.findById(id).orElseThrow(() -> new RuntimeException("Habit not found"));

	    habit.setCompleted(!habit.isCompleted());
	    Habit saved = habitRepo.save(habit);

	    return toDto(saved);
	}

	public HabitResponseDTO toDto(Habit habit) {

		HabitResponseDTO dto = new HabitResponseDTO();

		dto.setId(habit.getId());
		dto.setName(habit.getName());
		dto.setCompleted(habit.isCompleted());

		return dto;
	}
}
