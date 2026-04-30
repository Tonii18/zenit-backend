package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseRequestDTO {
	
	private String weekDay;
	private String name;
	private int sets;
	private int reps;
	private double weight;

}
