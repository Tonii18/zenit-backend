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
import com.example.demo.models.HabitRequestDTO;
import com.example.demo.models.HabitResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.HabitService;

@RestController
public class HabitController {
	
	@Autowired
	private HabitService habitService;
	
	@Autowired
	private UserRepository userRepo;
	
	// Save new Habit
	
	@PostMapping("/habit/save")
	public ResponseEntity<HabitResponseDTO> saveOrUpdate(@RequestBody HabitRequestDTO request) {
		try {
			Long userId = getUserId();
			HabitResponseDTO response = habitService.createHabit(request, userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Get all Habits
	
	@GetMapping("/habit/show")
	public ResponseEntity<List<HabitResponseDTO>> getHistory(){
		try {
			Long userId = getUserId();
			List<HabitResponseDTO> response = habitService.getHabits(userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Delete Habit
	
	@DeleteMapping("/habit/delete/{id}")
	public ResponseEntity<Void> deleteHabit(@PathVariable Long id){
		try {
			if(habitService.deleteHabit(id) == 0) {
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Check habit
	
	@PostMapping("/habit/check/{id}")
	public ResponseEntity<HabitResponseDTO> toggleHabit(@PathVariable Long id) {
	    try {
	        HabitResponseDTO response = habitService.checkHabit(id);
	        
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().build();
	    }
	}
	
	public Long getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		
		return user.getId();
	}

}
