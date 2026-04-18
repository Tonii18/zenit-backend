package com.example.demo.services;

import java.util.List;

import com.example.demo.models.JournalRequestDTO;
import com.example.demo.models.JournalResponseDTO;

public interface JournalService {
	
	JournalResponseDTO createEntry(JournalRequestDTO request, String email);
	List<JournalResponseDTO> getEntries(String email);
	int deleteEntry(Long id);

}
