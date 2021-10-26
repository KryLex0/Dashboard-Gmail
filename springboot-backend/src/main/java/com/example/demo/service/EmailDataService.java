package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.EmailData;
import com.example.demo.repository.EmailDataRepository;

@Service
public class EmailDataService {

	@Autowired
	EmailDataRepository emailRepo;
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<EmailData> findAllEmailData() {
	      System.out.println(" -- finding all emails Data --");
	      return this.emailRepo.findAll();
	}
	
	public void deleteEmailData(String userID) {//EmailData mailData
		String sql = "DELETE FROM email_data WHERE id_commercial='"
				+ userID + "'";
		
		jdbcTemplate.execute(sql);
	}
	
	public void saveEmailData(EmailData emailData) {//EmailData mailData		
		String sql = "INSERT INTO email_data (id_commercial, email, date, heure, is_sender, is_receiver) VALUES('" 
		+ emailData.getIdCommercial() + "', '" 
		+ emailData.getEmail() + "', '" 
		+ emailData.getDate() + "', '"
		+ emailData.getHeure() + "', "
		+ emailData.getIsSender() + ", " 
		+ emailData.getIsReceiver() +  ")";

        jdbcTemplate.execute(sql);
		
	}
	
	
	/*
	public void saveEmailData(String id_commercial, String email, boolean is_sender, boolean is_receiver) {//EmailData mailData
		this.emailRepo.save(new EmailData(id_commercial, email, is_sender, is_receiver));
	}
	*/
	
	
}
