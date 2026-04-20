package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.models.DailyStepsRequestDTO;
import com.example.demo.models.DailyStepsResponseDTO;
import com.example.demo.models.WeekStatsResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.DailyStepsService;

@RestController
public class DailyStepsController {
	
	@Autowired
	private DailyStepsService dailyService;
	
	@Autowired
	private UserRepository userRepo;
	
	// Save or update today's steps
	
	@PostMapping("/steps/save")
	public ResponseEntity<DailyStepsResponseDTO> saveOrUpdate(@RequestBody DailyStepsRequestDTO request) {
		try {
			Long userId = getUserId();
			DailyStepsResponseDTO response = dailyService.saveOrUpdateDailyRecord(userId, request);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Get total steps from today
	
	@GetMapping("/steps/today")
	public ResponseEntity<DailyStepsResponseDTO> getToday(){
		try {
			Long userId = getUserId();
			DailyStepsResponseDTO response = dailyService.getTodayRecord(userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Get stats from current week
	
	@GetMapping("/steps/week")
	public ResponseEntity<WeekStatsResponseDTO> getWeekStats(){
		try {
			Long userId = getUserId();
			WeekStatsResponseDTO response = dailyService.getCurrentWeekStats(userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Get full history
	
	@GetMapping("/steps/history")
	public ResponseEntity<List<DailyStepsResponseDTO>> getHistory(){
		try {
			Long userId = getUserId();
			List<DailyStepsResponseDTO> response = dailyService.getHistory(userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	// Get Id from authenticated user
	public Long getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		
		return user.getId();
	}

}
