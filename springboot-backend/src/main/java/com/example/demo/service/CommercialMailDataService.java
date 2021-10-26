package com.example.demo.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.GmailAPI;
import com.example.demo.model.CommercialData;
import com.example.demo.model.CommercialMailData;
import com.example.demo.repository.CommercialMailDataRepository;

@Service
public class CommercialMailDataService {

	@Autowired
	CommercialMailDataRepository commercialMailDataRepo;
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public void findAllEmailData() {
	      System.out.println(" -- finding all emails Data --");
	      commercialMailDataRepo.findAll().forEach(System.out::println);
	  }
	
	
	public List<CommercialMailData> getCommercialMailData() {//EmailData mailData
		return commercialMailDataRepo.findAll();
	}
	
	/*
	public CommercialMailData getCommercialMailDataByDate(String idCommercial, Date dateStart, Date dateEnd) {//EmailData mailData
		return commercialMailDataRepo.findCommercialMailDataByDate(idCommercial, dateStart, dateEnd);
	}
	*/
	
	public List<CommercialMailData> findCommercialMailDataByIdCommercial(String idCommercial) {//EmailData mailData
		return commercialMailDataRepo.findByIdCommercial(idCommercial);
	}
	
	public void updateDatas(String idCommercial) throws Exception {//EmailData mailData
		GmailAPI.saveCommercialMailDataById(idCommercial);
		
	}
	
	public void updateCommercialMailData(CommercialMailData commercialMailData) {//EmailData mailData
		
		String sql = "UPDATE commercial_mail_data SET nb_mails_received=" 
		+ commercialMailData.getNbMailsReceived() + ", nb_mails_sent="
		+ commercialMailData.getNbMailsSent() + " WHERE id_commercial='"
		+ commercialMailData.getIdCommercial() + "' AND date_start_week='"
		+ commercialMailData.getDateStartWeek() + "' AND date_end_week='"
		+ commercialMailData.getDateEndWeek() + "'"  ;

        jdbcTemplate.execute(sql);
		
	}
	
	
	
	public void saveCommercialMailData(CommercialMailData commercialMailData) {//EmailData mailData
		//this.commercialDataRepo.save(commercialData);
		
		String sql = "INSERT INTO commercial_mail_data (id_commercial, date_start_week, date_end_week, nb_mails_received, nb_mails_sent) VALUES('" 
		+ commercialMailData.getIdCommercial() + "', '" 
		+ commercialMailData.getDateStartWeek() + "', '" 
		+ commercialMailData.getDateEndWeek() + "', '" 
		+ commercialMailData.getNbMailsReceived() + "', '"
		+ commercialMailData.getNbMailsSent() + "')";

        jdbcTemplate.execute(sql);
		
	}
	
	/*
	public void saveCommercialMailData(CommercialMailData commercialMailData) {//EmailData mailData
		//EmailData mailData = emailData;
		this.commercialMailDataRepo.save(commercialMailData);
	}
	*/
	public void saveCommercialMailData(String id_commercial, Date date_start_week, Date date_end_week, Long nb_mails_received, Long nb_mails_sent) {//EmailData mailData
		this.commercialMailDataRepo.save(new CommercialMailData(id_commercial, date_start_week, date_end_week, nb_mails_received, nb_mails_sent));
	}

/*
	public void reloadCommercialMailData(String idCommercial) throws Exception {
		GmailAPI.saveCommercialMailData(idCommercial);
	}
	*/
	
	
}
