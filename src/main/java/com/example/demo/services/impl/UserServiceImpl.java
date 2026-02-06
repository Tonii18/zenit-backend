package com.example.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.entities.UserProfile;
import com.example.demo.models.UserProfileDTO;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepo;
	
	@Autowired
	@Qualifier("userProfileRepository")
	private UserProfileRepository userProfileRepo;

	@Override
	public UserProfileDTO getCurrentUser() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || !authentication.isAuthenticated()) {
    		throw new IllegalStateException("There is no authenticated user");
    	}
		
		String email = authentication.getName();
		
		User userEntity = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
		
		UserProfile userProfile = userEntity.getProfile();
		
		UserProfileDTO currentUser = new UserProfileDTO();
		
		currentUser.setEmail(userEntity.getEmail());
		currentUser.setHeightCm(userProfile.getHeightCm());
		currentUser.setWeightKg(userProfile.getWeightKg());
		currentUser.setAge(userProfile.getAge());
		currentUser.setGender(userProfile.getGender());
		currentUser.setDailyStepsGoal(userProfile.getDailyStepsGoal());
		
		return currentUser;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || !authentication.isAuthenticated()) {
    		throw new IllegalStateException("There is no authenticated user");
    	}
		
		String email = authentication.getName();
		
		User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
		
		String name = user.getUsername();
		
		return name;
	}

}
