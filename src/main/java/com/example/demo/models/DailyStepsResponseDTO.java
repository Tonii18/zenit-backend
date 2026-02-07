package com.example.demo.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyStepsResponseDTO {
	
	private Long id;
	private LocalDate date;
	private int steps;
	private double distanceKm;
	private double caloriesBurned;
	private boolean goalAchieved;
	private double dailyStepsGoal;

}
