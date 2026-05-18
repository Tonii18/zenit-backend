package com.example.demo.services;

import java.util.List;

import com.example.demo.models.JournalRequestDTO;
import com.example.demo.models.JournalResponseDTO;

public interface JournalService {
	
	JournalResponseDTO createEntry(JournalRequestDTO request, Long userId);
	List<JournalResponseDTO> getEntries(Long userId);
	int deleteEntry(Long id);

}
