package com.example.demo.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.PersonalJournal;
import com.example.demo.entities.User;
import com.example.demo.models.JournalRequestDTO;
import com.example.demo.models.JournalResponseDTO;
import com.example.demo.repository.PersonalJournalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.JournalService;

@Service
public class JournalServiceImpl implements JournalService {

	private PersonalJournalRepository journalRepo;
	private UserRepository userRepo;

	public JournalServiceImpl(PersonalJournalRepository journalRepo, UserRepository userRepo) {
		this.journalRepo = journalRepo;
		this.userRepo = userRepo;
	}

	@Override
	public JournalResponseDTO createEntry(JournalRequestDTO request, Long userId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		PersonalJournal entry = new PersonalJournal();
		
		entry.setUser(user);
		entry.setTitle(request.getTitle());
		entry.setContent(request.getContent());
		entry.setEntryDate(LocalDate.now());
		entry.setEntryTime(LocalTime.now());
		
		PersonalJournal saved = journalRepo.save(entry);
		
		return toDto(saved);
	}

	@Override
	public List<JournalResponseDTO> getEntries(Long userId) {
		// TODO Auto-generated method stub
		List<JournalResponseDTO> results = new ArrayList<>();
		
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		List<PersonalJournal> entries = journalRepo.findByUserIdOrderByEntryDateDescEntryTimeDesc(user.getId());
		
		for(PersonalJournal entry: entries) {
			results.add(toDto(entry));
		}
		
		return results;
	}
	
	@Override
	public int deleteEntry(Long id) {
		// TODO Auto-generated method stub
		if(!journalRepo.existsById(id)) {
			throw new IllegalArgumentException("This entry does not exist, so it cannot be deleted");
		}
		journalRepo.deleteById(id);
		
		return 0;
	}
	
	public JournalResponseDTO toDto(PersonalJournal entry) {
		
        JournalResponseDTO dto = new JournalResponseDTO();
        
        dto.setId(entry.getId());
        dto.setTitle(entry.getTitle());
        dto.setContent(entry.getContent());
        dto.setEntryDate(entry.getEntryDate());
        dto.setEntryTime(entry.getEntryTime());
        
        return dto;
    }
}
