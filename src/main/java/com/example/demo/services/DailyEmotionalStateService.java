package com.example.demo.services;

import java.util.List;

import com.example.demo.models.DailyEmotionalStateRequestDTO;
import com.example.demo.models.DailyEmotionalStateResponseDTO;
import com.example.demo.models.HabitResponseDTO;

public interface DailyEmotionalStateService {
	
	DailyEmotionalStateResponseDTO createRecord(DailyEmotionalStateRequestDTO request, Long userId);
	
	List<DailyEmotionalStateResponseDTO> getMonthRecords(Long userId);
}
