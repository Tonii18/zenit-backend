package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.models.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;

@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepo;
	
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
		if(userRepo.findByEmail(userDTO.getEmail()).isPresent()) {
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

}
