package com.example.demo.services.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DailyStepsRecord;
import com.example.demo.entities.User;
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
		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		Optional<DailyStepsRecord> existingRecord = stepsRepo.findByUserAndDate(user, request.getDate());
		
		DailyStepsRecord record;
		
		// WE CHECK WHETHER CURRENT RECORD EXISTS OR NOT. IF EXISTS, WE UPDATED IT, OTHERWHISE WE CREATE NEW RECORD
		
		if(existingRecord.isPresent()) {
			record = existingRecord.get();
			record.setSteps(request.getSteps());
			record.setDistanceKm(request.getDistanceKm());
			record.setCaloriesBurned(request.getCaloriesBurned());
		}else {
			record = new DailyStepsRecord();
			
			record.setUser(user);
			record.setDate(request.getDate());
			record.setSteps(request.getSteps());
			record.setDistanceKm(request.getDistanceKm());
			record.setCaloriesBurned(request.getCaloriesBurned());
		}
		
		// VERIFY IF THE DAILY GOAL HAS BEEN ACHIEVED
		
		double dailyGoal = user.getProfile().getDailyStepsGoal();
		boolean achieved = request.getSteps() >= dailyGoal;
		
		record.setGoalAchieved(achieved);
		
		stepsRepo.save(record);
		
		return mapToResponse(record, dailyGoal);
	}

	@Override
	public DailyStepsResponseDTO getTodayRecord(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		LocalDate today = LocalDate.now();
		Optional<DailyStepsRecord> record = stepsRepo.findByUserAndDate(user, today);
		
		double dailyGoal = user.getProfile().getDailyStepsGoal();
		
		// IF THE RECORD EXISTS WE GET IT AND TURN THE ENTITY INTO A DTO, OTHERWISE WE CREATE AN EMPTY DTO AND RETURN IT
		
		if(record.isPresent()) {
			return mapToResponse(record.get(), dailyGoal);
		}else {
			return createEmptyResponse(today, dailyGoal);
		}
	}

	@Override
	public WeekStatsResponseDTO getCurrentWeekStats(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		// AS WE ALWAYS CALCULATE THE WEEK FROM MONDAY TO SUNDAY, FIRST WE MUST CALCULATE WHEN IS MONDAY
		
		LocalDate today = LocalDate.now();
		LocalDate monday = today.with(DayOfWeek.MONDAY);
		LocalDate sunday = monday.plusDays(6);
		
		List<DailyStepsRecord> weekRecords = stepsRepo.findByUserAndDateBetweenOrderByDateAsc(user, monday, sunday);
		 
		double dailyGoal = user.getProfile().getDailyStepsGoal();
		
		// WE CREATE AS LIST WITH THE SEVEN DAYS OF A WEEK (MONDAY TO SUNDAY)
		
		List<DailyStepsResponseDTO> dailyResponses = new ArrayList<>();
		
		for (int i = 0; i < 7; i++) {
            LocalDate currentDate = monday.plusDays(i);
            
            Optional<DailyStepsRecord> recordForDay = weekRecords.stream()
                    .filter(r -> r.getDate().equals(currentDate))
                    .findFirst();

            if (recordForDay.isPresent()) {
                dailyResponses.add(mapToResponse(recordForDay.get(), dailyGoal));
            } else {
                dailyResponses.add(createEmptyResponse(currentDate, dailyGoal));
            }
        }
		
		// CALCULATE TOTALS
		
		int totalSteps = weekRecords.stream().mapToInt(DailyStepsRecord::getSteps).sum();
		double totalDistance = weekRecords.stream().mapToDouble(DailyStepsRecord::getDistanceKm).sum();
		double totalCalories = weekRecords.stream().mapToDouble(DailyStepsRecord::getCaloriesBurned).sum();
		int daysGoalAchieved = (int) weekRecords.stream().filter(DailyStepsRecord::isGoalAchieved).count();
		
		WeekStatsResponseDTO response = new WeekStatsResponseDTO();
		
		response.setDailyRecords(dailyResponses);
		response.setTotalSteps(totalSteps);
		response.setTotalDistanceKm(totalDistance);
		response.setTotalCalories(totalCalories);
		response.setDaysGoalAchieved(daysGoalAchieved);
		
		return response;
	}

	@Override
	public List<DailyStepsResponseDTO> getHistory(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		List<DailyStepsRecord> records = stepsRepo.findByUserOrderByDateDesc(user);
		double dailyGoal = user.getProfile().getDailyStepsGoal();
		
		return records.stream().map(record -> mapToResponse(record, dailyGoal)).toList();
	}

	@Override
	public DailyStepsResponseDTO createEmptyResponse(LocalDate date, double dailyGoal) {
		// TODO Auto-generated method stub
		DailyStepsResponseDTO response = new DailyStepsResponseDTO();
		
        response.setId(null);
        response.setDate(date);
        response.setSteps(0);
        response.setDistanceKm(0.0);
        response.setCaloriesBurned(0.0);
        response.setGoalAchieved(false);
        response.setDailyStepsGoal(dailyGoal);
        
        return response;
	}
	
	/*
	 * EXTRA METHOD TO MAP FROM ENTITY TO DTO
	 */
	
    public DailyStepsResponseDTO mapToResponse(DailyStepsRecord record, double dailyGoal) {
        DailyStepsResponseDTO response = new DailyStepsResponseDTO();
        
        response.setId(record.getId());
        response.setDate(record.getDate());
        response.setSteps(record.getSteps());
        response.setDistanceKm(record.getDistanceKm());
        response.setCaloriesBurned(record.getCaloriesBurned());
        response.setGoalAchieved(record.isGoalAchieved());
        response.setDailyStepsGoal(dailyGoal);
        
        return response;
    }

}
