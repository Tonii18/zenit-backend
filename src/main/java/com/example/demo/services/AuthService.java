package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.entities.UserProfile;
import com.example.demo.models.UserDTO;
import com.example.demo.models.UserProfileDTO;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserProfileRepository userProfileRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider tokenProvider;

	public String login(String email, String password) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(auth);

		return tokenProvider.generateToken(auth);
	}

	public void register(UserDTO userDTO) {
		if (userRepo.findByEmail(userDTO.getEmail()).isPresent()) {
			throw new RuntimeException("This email is already taken");
		}

		User user = new User();

		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRole("ROLE_USER");
		user.setVerified(false);

		userRepo.save(user);
	}

	public boolean isVerified(String email) {
		return userRepo.findByEmail(email).map(User::isVerified).orElse(false);
	}
	
	public void saveUserProfile(UserProfileDTO dto) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String email = auth.getName();
		
		System.out.println("EMAIL RECIBIDO: " + dto.getEmail());
		
		User user = userRepo.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
		
		if(userProfileRepo.existsByUser(user)) {
			throw new RuntimeException("User profile already exists");
		}
		
		UserProfile profile = new UserProfile();
		
		profile.setUser(user);
		profile.setHeightCm(dto.getHeightCm());
		profile.setWeightKg(dto.getWeightKg());
		profile.setAge(dto.getAge());
		profile.setGender(dto.getGender());
		profile.setDailyStepsGoal(dto.getDailyStepsGoal());
		
		user.setProfile(profile);
		
		userProfileRepo.save(profile);
	}
}
