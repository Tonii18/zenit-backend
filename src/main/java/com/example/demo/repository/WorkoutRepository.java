package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.WorkoutExercise;

@Repository("workoutRepository")
public interface WorkoutRepository extends JpaRepository<WorkoutExercise, Long>{
	
	List<WorkoutExercise> findByUserIdAndWeekDay(Long userId, String weekDay);

}
