package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.entities.WorkoutExercise;
import com.example.demo.models.WorkoutExerciseRequestDTO;
import com.example.demo.models.WorkoutExerciseResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkoutRepository;
import com.example.demo.services.WorkoutExerciseService;

@Service("workoutExerciseService")
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService{
	
	@Autowired
	@Qualifier("workoutRepository")
	private WorkoutRepository workoutRepo;
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepo;

	@Override
	public WorkoutExerciseResponseDTO createExercise(WorkoutExerciseRequestDTO request, Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		WorkoutExercise exercise = new WorkoutExercise();
		
		exercise.setUser(user);
		exercise.setName(request.getName());
		exercise.setSets(request.getSets());
		exercise.setReps(request.getReps());
		exercise.setWeight(request.getWeight());
		
		WorkoutExerciseResponseDTO saved = toDto(exercise);
		
		return saved;
	}

	@Override
	public List<WorkoutExerciseResponseDTO> getExercises(Long userId, String weekDay) {
		// TODO Auto-generated method stub
		List<WorkoutExerciseResponseDTO> results = new ArrayList<>();
		
		List<WorkoutExercise> exercises = workoutRepo.findByUserIdAndDay(userId, weekDay);
		
		for(WorkoutExercise exercise: exercises) {
			results.add(toDto(exercise));
		}
		
		return results;
	}

	@Override
	public int deleteExercise(Long id) {
		// TODO Auto-generated method stub
		if(!workoutRepo.existsById(id)) {
			throw new IllegalArgumentException("This exercise does not exist, so it cannot be deleted");
		}
		workoutRepo.deleteById(id);
		
		return 0;
	}
	
	public WorkoutExerciseResponseDTO toDto(WorkoutExercise exercise) {
		
		WorkoutExerciseResponseDTO dto = new WorkoutExerciseResponseDTO();
		
		dto.setId(exercise.getId());
		dto.setName(exercise.getName());
		dto.setReps(exercise.getReps());
		dto.setSets(exercise.getSets());
		dto.setWeight(exercise.getWeight());
		
		return dto;
	}

}
