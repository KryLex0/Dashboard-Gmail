package com.example.demo.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.GmailAPI;
import com.example.demo.model.EmailData;
import com.example.demo.repository.EmailDataRepository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EmailDataController {
	
	@Autowired
	private EmailDataRepository emaildataRepository;

	@GetMapping("/emails")
	public List<EmailData> getEmailData(){
		return this.emaildataRepository.findAll();
	}
	
	@GetMapping("/emails/{idCommercial}")
	public List<EmailData> getEmailDataByIdCommercial(@PathVariable("idCommercial") String idCommercial){
		return this.emaildataRepository.findEmailDataByIdCommercial(idCommercial);
	}
	
	@GetMapping("/emails/is_receiver/{idCommercial}")
	public List<EmailData> getEmailDataIsSender(@PathVariable("idCommercial") String idCommercial){
		return this.emaildataRepository.findEmailDataIsReceiver(idCommercial);
	}
	
	@GetMapping("/emails/is_sender/{idCommercial}")
	public List<EmailData> getEmailDataIsReceiver(@PathVariable("idCommercial") String idCommercial){
		return this.emaildataRepository.findEmailDataIsSender(idCommercial);
	}
	
	@PutMapping("/emails/refresh/{idCommercial}")
	public void refreshEmailData(@PathVariable("idCommercial") String idCommercial) throws IOException, InterruptedException, ParseException{
		//return this.emaildataRepository.findAll();
		GmailAPI.saveEmailDataReceivedSent(idCommercial);
	}
/*
	@Autowired
	public EmailDataController(EmailDataRepository emaildataRepository, EmailData mailData) {
		this.emaildataRepository = emaildataRepository;
		this.emaildataRepository.save(mailData);
	}
	*/
	
}
