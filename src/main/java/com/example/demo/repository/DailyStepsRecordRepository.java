package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.DailyStepsRecord;
import com.example.demo.entities.User;

public interface DailyStepsRecordRepository extends JpaRepository<DailyStepsRecord, Long>{
	
	// Get data from an user on a fixed date
	
	Optional<DailyStepsRecord> findByUserAndDate(User user, LocalDate date);
	
	// Get all data from user within fixed term
	
	List<DailyStepsRecord> findByUserAndDateBetweenOrderByDateAsc(User user, LocalDate startDate, LocalDate endDate);
	
	// Get all data from user order by date
	
	 List<DailyStepsRecord> findByUserOrderByDateDesc(User user);
	 
	 // Verify whether exists data from an user within a date
	 
	 boolean existsByUserAndDate(User user, LocalDate date);

}
