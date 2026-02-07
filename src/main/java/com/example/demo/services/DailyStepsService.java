package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.models.DailyStepsRequestDTO;
import com.example.demo.models.DailyStepsResponseDTO;
import com.example.demo.models.WeekStatsResponseDTO;

public interface DailyStepsService {
	
	public DailyStepsResponseDTO saveOrUpdateDailyRecord(Long id, DailyStepsRequestDTO request);
	
	public DailyStepsResponseDTO getTodayRecord(Long userId);
	
	public WeekStatsResponseDTO getCurrentWeekStats(Long userId);
	
	public List<DailyStepsResponseDTO> getHistory(Long userId);
	
	public DailyStepsResponseDTO createEmptyResponse(LocalDate date, double dailyGoal);

}
