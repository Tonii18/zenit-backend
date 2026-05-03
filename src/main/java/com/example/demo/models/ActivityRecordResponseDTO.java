package com.example.demo.models;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRecordResponseDTO {
	
	private Long id;
	private String activityType;
	private double totalDistance;
	private int totalMinutes;
	private double totalCalories;
	private LocalDate date;
	private LocalTime time;

}
