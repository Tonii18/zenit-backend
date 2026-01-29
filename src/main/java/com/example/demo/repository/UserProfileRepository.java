package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.User;
import com.example.demo.entities.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	
	boolean existsByUser(User user);

}
