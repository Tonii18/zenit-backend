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
import com.example.demo.models.ActivityRecordRequestDTO;
import com.example.demo.models.ActivityRecordResponseDTO;
import com.example.demo.models.WorkoutExerciseResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ActivityRecordService;

@RestController
public class ActivityRecordController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ActivityRecordService activityService;
	
	// Save new activity
	
	@PostMapping("/activity/save")
	public ResponseEntity<ActivityRecordResponseDTO> save(@RequestBody ActivityRecordRequestDTO request) {
		try {
			Long userId = getUserId();
			ActivityRecordResponseDTO response = activityService.createActivity(request, userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Get list of activities
	
	@GetMapping("/activity/history")
	public ResponseEntity<List<ActivityRecordResponseDTO>> getActivities(){
		try {
			Long userId = getUserId();
			List<ActivityRecordResponseDTO> response = activityService.getActivities(userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Delete activity
	
	@DeleteMapping("/activity/delete/{id}")
	public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
		try {
			if(activityService.deleteActivity(id) == 0) {
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// TODO: handle exception
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
