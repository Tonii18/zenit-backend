package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
	
	private int heightCm;
	private int weightKg;
	private int age;
	private String gender;
	private double dailyStepsGoal;
}
