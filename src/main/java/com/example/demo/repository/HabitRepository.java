package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Habit;
import com.example.demo.entities.PersonalJournal;

@Repository("habitRepository")
public interface HabitRepository extends JpaRepository<Habit, Long>{
	
	List<Habit> findByUserId(Long userId);

}
