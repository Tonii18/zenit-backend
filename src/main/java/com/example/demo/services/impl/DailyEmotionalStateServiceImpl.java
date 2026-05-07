package com.example.demo.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DailyEmotionalState;
import com.example.demo.entities.User;
import com.example.demo.models.DailyEmotionalStateRequestDTO;
import com.example.demo.models.DailyEmotionalStateResponseDTO;
import com.example.demo.repository.DailyEmotionalStateRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.DailyEmotionalStateService;

@Service("dailyEmotionalStateService")
public class DailyEmotionalStateServiceImpl implements DailyEmotionalStateService{
	
	@Autowired
    @Qualifier("dailyEmotionalStateRepository")
    private DailyEmotionalStateRepository emotionalRepo;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepo;

	@Override
	public DailyEmotionalStateResponseDTO createRecord(DailyEmotionalStateRequestDTO request, Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		LocalDate today = LocalDate.now();
		
		if (emotionalRepo.existsByUserIdAndDate(userId, today)) {
            throw new RuntimeException("You have already record your emotional state today");
        }
		
		DailyEmotionalState emotion = new DailyEmotionalState();
		
		emotion.setUser(user);
		emotion.setEmotionalValue(request.getEmotionalValue());
		emotion.setDate(today);
		
		DailyEmotionalState saved = emotionalRepo.save(emotion);
		
		return toDto(saved);
	}

	@Override
	public List<DailyEmotionalStateResponseDTO> getMonthRecords(Long userId) {
		// TODO Auto-generated method stub
		LocalDate today = LocalDate.now();
		LocalDate firstDay = today.withDayOfMonth(1);
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());
		
		List<DailyEmotionalState> records = emotionalRepo.findByUserIdAndDateBetween(userId, firstDay, lastDay);
		List<DailyEmotionalStateResponseDTO> results = new ArrayList<>();
		
		for(DailyEmotionalState emotion: records) {
			results.add(toDto(emotion));
		}
		
		return results;
	}
	
	public DailyEmotionalStateResponseDTO toDto(DailyEmotionalState record) {
        DailyEmotionalStateResponseDTO dto = new DailyEmotionalStateResponseDTO();
        dto.setId(record.getId());
        dto.setEmotionalValue(record.getEmotionalValue());
        dto.setDate(record.getDate());
        return dto;
    }

}
