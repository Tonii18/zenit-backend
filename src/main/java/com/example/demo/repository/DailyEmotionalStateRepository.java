package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.DailyEmotionalState;

@Repository("dailyEmotionalStateRepository")
public interface DailyEmotionalStateRepository extends JpaRepository<DailyEmotionalState, Long>{
	
	List<DailyEmotionalState> findByUserAndDateBetween(Long userId, LocalDate beginning, LocalDate end);
	
	boolean existsByUserIdAndDate(Long userId, LocalDate date);
}
