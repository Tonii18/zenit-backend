package com.example.demo.services;

import java.util.List;

import com.example.demo.models.ActivityRecordRequestDTO;
import com.example.demo.models.ActivityRecordResponseDTO;
import com.example.demo.models.WorkoutExerciseResponseDTO;

public interface ActivityRecordService {
	
	ActivityRecordResponseDTO createActivity(ActivityRecordRequestDTO request, Long userId);
	List<ActivityRecordResponseDTO> getActivities(Long userId);
	int deleteActivity(Long id);

}
