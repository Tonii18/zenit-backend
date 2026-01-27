package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.email.EmailBody;
import com.example.demo.email.EmailPort;
import com.example.demo.entities.User;
import com.example.demo.models.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailPort emailPort;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> request){
		try {
			String token = authService.login(request.get("email"), request.get("password"));
			return ResponseEntity.ok(Map.of("token", token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password incorrect");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO userDto){
		authService.register(userDto);
		return ResponseEntity.ok("User registered succesfully!");
	}
	
	/*
	 * These methods are used to verify an user by using his email
	 */
	
	@PostMapping("/send/verification")
	public boolean sendVerification(@RequestBody Map<String, String> body) {
		User user = userRepo.findByEmail(body.get("email")).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		
		String url = "http://localhost:8080/auth/verify?userId=" + user.getId();
		
		String content =
	            "<h2>Verificación de cuenta</h2>" +
	            "<p>Pulsa el botón para verificar tu cuenta de Zenit</p>" +
	            "<a href=\"" + url + "\">Verificar cuenta</a>";
		
		EmailBody emailBody = new EmailBody(user.getEmail(), content, "Verificación de cuenta");
		
		return emailPort.sendEmail(emailBody);
	}
	
	@GetMapping("/verify")
	public String verifyUser(@RequestParam Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		
		user.setVerified(true);
		userRepo.save(user);
		
		return "Cuenta verificada correctamente";
	}

}
