package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.PersonalJournal;

@Repository("personalJournalRepository")
public interface PersonalJournalRepository extends JpaRepository<PersonalJournal, Long>{
	
	List<PersonalJournal> findByUserIdOrderByEntryDateDescEntryTimeDesc(Long userId);

}
