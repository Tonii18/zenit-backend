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

import com.example.demo.models.JournalRequestDTO;
import com.example.demo.models.JournalResponseDTO;
import com.example.demo.services.JournalService;
import com.example.demo.services.UserService;

@RestController
public class JournalController {
	
	@Autowired
	private JournalService journalService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/journal/create")
	public ResponseEntity<JournalResponseDTO> createEntry(@RequestBody JournalRequestDTO request) {
		try {
			Long userId = userService.getCurrentUserId();
			
			JournalResponseDTO response = journalService.createEntry(request, userId);
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/journal/entries")
	public ResponseEntity<List<JournalResponseDTO>> getEntries() {
		try {
			Long userId = userService.getCurrentUserId();
			
			List<JournalResponseDTO> entries = journalService.getEntries(userId);
			
			return ResponseEntity.ok(entries);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/journal/delete/{id}")
	public ResponseEntity<Void> deleteEntry(@PathVariable Long id){
		try {
			if(journalService.deleteEntry(id) == 0) {
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().build();
		}
	}

}
