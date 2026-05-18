package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.models.WorkoutExerciseRequestDTO;
import com.example.demo.models.WorkoutExerciseResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.services.WorkoutExerciseService;

@RestController
public class WorkoutExerciseController {
	
	@Autowired
	private WorkoutExerciseService workoutService;
	
	@Autowired
	private UserService userService;
	
	// Save new exercise
	
	@PostMapping("/workout/save")
	public ResponseEntity<WorkoutExerciseResponseDTO> save(@RequestBody WorkoutExerciseRequestDTO request) {
		try {
			Long userId = userService.getCurrentUserId();
			WorkoutExerciseResponseDTO response = workoutService.createExercise(request, userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Get list of exercises filtered by day of the week
	
	@GetMapping("/workout/{weekDay}")
	public ResponseEntity<List<WorkoutExerciseResponseDTO>> getExercises(@PathVariable String weekDay){
		try {
			Long userId = userService.getCurrentUserId();
			List<WorkoutExerciseResponseDTO> response = workoutService.getExercises(userId, weekDay);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Delete exercise
	
	@DeleteMapping("/workout/delete/{id}")
	public ResponseEntity<Void> deleteExercise(@PathVariable Long id){
		try {
			if(workoutService.deleteExercise(id) == 0) {
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
}
