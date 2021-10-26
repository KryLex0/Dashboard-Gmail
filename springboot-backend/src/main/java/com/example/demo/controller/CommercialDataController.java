package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.GmailAPI;
import com.example.demo.model.CommercialData;
import com.example.demo.model.CommercialMailData;
import com.example.demo.repository.CommercialDataRepository;
import com.example.demo.service.CommercialDataService;
import com.example.demo.service.CommercialMailDataService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CommercialDataController {
	
	@Autowired
	private CommercialDataRepository commercialDataRepo;
	
	@Autowired
	CommercialDataService commercialDataService = new CommercialDataService();
	
	@Autowired
	CommercialMailDataService commercialMailDataService = new CommercialMailDataService();
	
	@GetMapping("/commercial_data")
	public List<CommercialData> getCommercialData(){
		return this.commercialDataRepo.findAllCommercialData();
	}
	
	
	
	@GetMapping("/commercial_data/{id}")
	public Optional<CommercialData> getCommercialDataById(@PathVariable("id") Long id){
		return this.commercialDataRepo.findById(id);
	}
	
	@PutMapping("/commercial_data/{id}/edit")
	public void editCommercialData(@PathVariable("id") Long id, @RequestBody CommercialData commercialData) throws Exception {
		this.commercialDataService.updateCommercialData(id, commercialData);
	}
	
	@PutMapping("/commercial_data/refresh")
	public void refreshCommercialData() throws Exception {
		GmailAPI.updateCommercialData();
	}
	
	
	
	
	/*
	@PutMapping("/commercial_data/{id}/delegate")
	public void delegateCommercialData(@PathVariable("id") Integer idCommercial) {
		this.commercialDataService.delegateCommercialData(idCommercial);
	}
	*/
	/*
	public void refreshCommercialData(CommercialData commercialData) throws Exception {
		this.commercialDataService.reloadCommercialData1(commercialData);
		//this.refreshCommercialData();
	}*/
	
	/*
	@PutMapping("/commercial_data/{id}/refresh")
	public void refreshCommercialData(@PathVariable("id") Long id, @RequestBody CommercialData commercialData) throws Exception {
		this.commercialDataService.reloadCommercialData1(commercialData);
	}
	
	@PutMapping("/commercial_data/refresh")
	public void refreshCommercialData() throws Exception {
		this.commercialDataService.reloadCommercialData1();
	}
	*/
	/*
	@PutMapping("/commercial_data/{id}/delegate")
	public void delegateCommercialData(@PathVariable("id") Integer id, @RequestBody CommercialData commercialData) {
		CommercialData commercialD = new CommercialData(commercialData);
		commercialD.setIsDelegated(true);
		this.commercialDataService.updateCommercialData(id, commercialD);
		
	}
	*/
	
	
	/*
	public List<CommercialData> getCommercialDataDelegated(){
		return this.commercialDataRepo.findCommercialWithDelegation();
	}
	*/
/*
	@Autowired
	public EmailDataController(EmailDataRepository emaildataRepository, EmailData mailData) {
		this.emaildataRepository = emaildataRepository;
		this.emaildataRepository.save(mailData);
	}
	*/
	
}
