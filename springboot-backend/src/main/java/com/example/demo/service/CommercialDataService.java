package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.GmailAPI;
import com.example.demo.model.CommercialData;
import com.example.demo.repository.CommercialDataRepository;



@Service
public class CommercialDataService {

	@Autowired
	CommercialDataRepository commercialDataRepo;
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	
	public List<CommercialData> findAllCommercialData() {
	      return commercialDataRepo.findAllCommercialData();
	  }
	
	public List<CommercialData> getDelegatedCommercial() {//EmailData mailData
		return commercialDataRepo.findCommercialWithDelegation();
	}
	
	public void updateCommercialData(Long id, CommercialData commercialData) throws IOException {//EmailData mailData
		String nomCommercial = "";
		String emailCommercial = "";
		
		int nbMailsReceived = 0;
		int nbMailsSent = 0;
		int nbTotalMails = 0;
		
		boolean isDelegated = false;
		
		if(commercialData.getIsDelegated()) {
			nomCommercial = commercialData.getNomCommercial();
			emailCommercial = commercialData.getEmailCommercial();
			
			nbMailsReceived = GmailAPI.getCountMailsReceived(commercialData.getIdCommercial());
			nbMailsSent = GmailAPI.getCountMailsSent(commercialData.getIdCommercial());
			nbTotalMails = nbMailsReceived + nbMailsSent;
			
			isDelegated = true;
		}else {
			nomCommercial = commercialData.getNomCommercial();
			emailCommercial = commercialData.getEmailCommercial();
			
			nbMailsReceived = 0;
			nbMailsSent = 0;
			nbTotalMails = 0;
			
			isDelegated = false;
		}
		
		//this.commercialDataRepo.save(commercialData);
		
		String sql = "UPDATE commercial_data SET nom_commercial='"  
		+ nomCommercial + "', email_commercial='" 
		+ emailCommercial + "', nb_mails_received=" 
		+ nbMailsReceived + ", nb_mails_sent="
		+ nbMailsSent + ", total_mails="
		+ nbTotalMails + ", is_delegated='"
		+ isDelegated + "' WHERE id="
		+ id + "" ;
		
        jdbcTemplate.execute(sql);
		
	}
	
	public void saveCommercialData(CommercialData commercialData) {//EmailData mailData
		//this.commercialDataRepo.save(commercialData);
		
		String sql = "INSERT INTO commercial_data (id_commercial, nom_commercial, email_commercial, nb_mails_received, nb_mails_sent, total_mails, is_delegated) VALUES('" 
		+ commercialData.getIdCommercial() + "', '" 
		+ commercialData.getNomCommercial() + "', '" 
		+ commercialData.getEmailCommercial() + "', " 
		+ commercialData.getNbMailsReceived() + ", "
		+ commercialData.getNbMailsSent() + ", "
		+ commercialData.getTotalMails() + ", '"
		+ commercialData.getIsDelegated() + "')";

        jdbcTemplate.execute(sql);
		
	}
	public void saveCommercialData(String id_commercial, String nom_commercial, String email_commercial, Long nb_mails_received, Long nb_mails_sent, Long total_mails, boolean isDelegated) {//EmailData mailData
		this.commercialDataRepo.save(new CommercialData(id_commercial, nom_commercial, email_commercial, nb_mails_received, nb_mails_sent, total_mails, isDelegated));
	}
	
	/*
	public void reloadCommercialData() throws Exception {
		GmailAPI.saveCommercialData();
	}*/
	
	/*
	public void reloadCommercialData1(CommercialData commercialData) throws Exception {
		GmailAPI.updateCommercialData1(commercialData);
	}
	*/
	
}
