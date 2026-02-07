package com.example.demo.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.DailyStepsRequestDTO;
import com.example.demo.models.DailyStepsResponseDTO;
import com.example.demo.models.WeekStatsResponseDTO;
import com.example.demo.repository.DailyStepsRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.DailyStepsService;

@Service
public class DailyStepsServiceImpl implements DailyStepsService{
	
	@Autowired
	private DailyStepsRecordRepository stepsRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public DailyStepsResponseDTO saveOrUpdateDailyRecord(Long id, DailyStepsRequestDTO request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DailyStepsResponseDTO getTodayRecord(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeekStatsResponseDTO getCurrentWeekStats(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DailyStepsResponseDTO> getHistory(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DailyStepsResponseDTO createEmptyResponse(LocalDate date, double dailyGoal) {
		// TODO Auto-generated method stub
		return null;
	}

}
