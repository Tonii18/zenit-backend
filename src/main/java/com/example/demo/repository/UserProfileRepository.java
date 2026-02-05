package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;
import com.example.demo.entities.UserProfile;

@Repository("userProfileRepository")
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	
	boolean existsByUser(User user);
	
	Optional<UserProfile> findById(Long id);

}
