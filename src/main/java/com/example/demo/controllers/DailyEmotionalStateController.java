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
import com.example.demo.models.DailyEmotionalStateRequestDTO;
import com.example.demo.models.DailyEmotionalStateResponseDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.DailyEmotionalStateService;
import com.example.demo.services.UserService;

@RestController
public class DailyEmotionalStateController {
	
	@Autowired
	private DailyEmotionalStateService emotionalService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/emotional/save")
	public ResponseEntity<DailyEmotionalStateResponseDTO> save(@RequestBody DailyEmotionalStateRequestDTO request) {
		try {
			Long userId = userService.getCurrentUserId();
			DailyEmotionalStateResponseDTO response = emotionalService.createRecord(request, userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/emotional/show")
	public ResponseEntity<List<DailyEmotionalStateResponseDTO>> showRecords(){
		try {
			Long userId = userService.getCurrentUserId();
			List<DailyEmotionalStateResponseDTO> response = emotionalService.getMonthRecords(userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
}
