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
public class ActivityRecordRequestDTO {
	
	private String activityType;
	private double totalDistance;
	private int totalMinutes;
	private LocalDate date;
	private LocalDate time;

}
