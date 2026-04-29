package com.example.demo.services;

import java.util.List;

import com.example.demo.models.HabitRequestDTO;
import com.example.demo.models.HabitResponseDTO;

public interface HabitService {
	
	HabitResponseDTO createHabit(HabitRequestDTO request, Long userId);
	List<HabitResponseDTO> getHabits(Long userId);
	int deleteHabit(Long id);
	HabitResponseDTO checkHabit(Long id);
}
