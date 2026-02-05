package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.UserProfileDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/account/profile")
	public ResponseEntity <UserProfileDTO> getCurrentUser(){
		UserProfileDTO currentUser = userService.getCurrentUser();
		
		return ResponseEntity.ok(currentUser);
	}

}
