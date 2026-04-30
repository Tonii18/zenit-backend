package com.example.demo.services;

import java.util.List;

import com.example.demo.models.WorkoutExerciseRequestDTO;
import com.example.demo.models.WorkoutExerciseResponseDTO;

public interface WorkoutExerciseService {
	
	WorkoutExerciseResponseDTO createExercise(WorkoutExerciseRequestDTO request, Long userId);
	List<WorkoutExerciseResponseDTO> getExercises(Long userId, String weekDay);
	int deleteExercise(Long id);
	
}
