package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CommercialData;
import com.example.demo.model.CommercialMailData;
import com.example.demo.repository.CommercialMailDataRepository;
import com.example.demo.service.CommercialDataService;
import com.example.demo.service.CommercialMailDataService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CommercialMailDataController {
	
	@Autowired
	private CommercialMailDataRepository commercialMailDataRepo;
	
	@Autowired
	CommercialMailDataService commercialMailDataService = new CommercialMailDataService();

	@GetMapping("/commercial_mail_data")
	public List<CommercialMailData> getCommercialMailData(){
		return this.commercialMailDataRepo.findCommercialMailDataOrderByDateStartWeek();
	}
	
	@GetMapping("/commercial_mail_data/{id}")
	public Optional<CommercialMailData> getCommercialDataById(@PathVariable("id") Long id){
		return this.commercialMailDataRepo.findById(id);
	}
	
	@PutMapping("/commercial_mail_data/refresh_mails_data/{id}")
	public void refreshCommercialMailData(@PathVariable("id") String id, @RequestBody CommercialData commercialData) throws Exception{
		System.out.println(commercialData + "--------------------");
		this.commercialMailDataService.updateDatas(commercialData.getIdCommercial());
	}
	
	/*
	@PutMapping("/commercial_mail_data/{id}/reload_stats")
	public void reloadCommercialMailData(@PathVariable("id") Integer id, @RequestBody CommercialData commercialData) {
		this.commercialMailDataService.reloadCommercialMailData(id);//.updateCommercialData(id, commercialData);
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
