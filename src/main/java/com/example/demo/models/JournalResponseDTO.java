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
public class JournalResponseDTO {
	
	private Long id;
	private String title;
	private String content;
	private LocalDate entryDate;
	private LocalTime entryTime;

}
