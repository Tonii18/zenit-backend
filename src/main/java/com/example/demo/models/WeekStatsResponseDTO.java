package com.example.demo.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeekStatsResponseDTO {
	
	private List<DailyStepsResponseDTO> dailyRecords;
	private int totalSteps;
	private double totalDistanceKm;
	private double totalCalories;
	private int daysGoalAchieved;

}
