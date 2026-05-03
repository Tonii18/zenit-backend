package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ActivityRecord;
import com.example.demo.entities.User;
import com.example.demo.models.ActivityRecordRequestDTO;
import com.example.demo.models.ActivityRecordResponseDTO;
import com.example.demo.repository.ActivityRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ActivityRecordService;

@Service("activityRecordService")
public class ActivityRecordServiceImpl implements ActivityRecordService {

	@Autowired
	@Qualifier("activityRecordRepository")
	private ActivityRecordRepository activityRepo;

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepo;

	@Override
	public ActivityRecordResponseDTO createActivity(ActivityRecordRequestDTO request, Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		ActivityRecord activity = new ActivityRecord();
		
		double userWeight = user.getProfile().getWeightKg();
		
		double totalCalories = 0;
		
		if(request.getActivityType().equalsIgnoreCase("bicicleta")) {
			totalCalories = 8 * userWeight * (request.getTotalMinutes() / 60);
		}else if(request.getActivityType().equalsIgnoreCase("caminar")) {
			totalCalories = userWeight * request.getTotalDistance();
		}else if(request.getActivityType().equalsIgnoreCase("correr")) {
			totalCalories = userWeight * request.getTotalDistance();
		}
		
		activity.setActivityType(request.getActivityType());
		activity.setTotalDistance(request.getTotalDistance());
		activity.setTotalMinutes(request.getTotalMinutes());
		activity.setTotalCalories(totalCalories);
		activity.setUser(user);
		activity.setTime(request.getTime());
		activity.setDate(request.getDate());
		
		ActivityRecord saved = activityRepo.save(activity);
		
		return toDto(saved);
	}

	@Override
	public List<ActivityRecordResponseDTO> getActivities(Long userId) {
		// TODO Auto-generated method stub
		List<ActivityRecordResponseDTO> results = new ArrayList<>();

		List<ActivityRecord> activities = activityRepo.findByUserId(userId);

		for (ActivityRecord activity : activities) {
			results.add(toDto(activity));
		}

		return results;
	}

	@Override
	public int deleteActivity(Long id) {
		// TODO Auto-generated method stub
		if (!activityRepo.existsById(id)) {
			throw new IllegalArgumentException("This activity does not exist, so it cannot be deleted");
		}
		activityRepo.deleteById(id);

		return 0;
	}

	public ActivityRecordResponseDTO toDto(ActivityRecord activity) {

		ActivityRecordResponseDTO dto = new ActivityRecordResponseDTO();

		dto.setId(activity.getId());
		dto.setActivityType(activity.getActivityType());
		dto.setTotalMinutes(activity.getTotalMinutes());
		dto.setTotalDistance(activity.getTotalDistance());
		dto.setTotalCalories(activity.getTotalCalories());
		dto.setTime(activity.getTime());
		dto.setDate(activity.getDate());

		return dto;
	}
}
