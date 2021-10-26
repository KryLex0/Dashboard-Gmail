package com.example.demo.model;

import java.sql.Date;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_data")  //definition d'un nom de table
public class EmailData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_commercial") //definition d'un nom de colonne
	private String id_commercial;
	
	@Column(name = "email") //definition d'un nom de colonne
	private String email;
	
	@Column(name = "is_sender") //definition d'un nom de colonne
	private boolean is_sender;

	@Column(name = "is_receiver") //definition d'un nom de colonne
	private boolean is_receiver;
	
	@Column(name = "date") //definition d'un nom de colonne
	private Date date;

	@Column(name = "heure") //definition d'un nom de colonne
	private String heure;

	public EmailData() {}
	
	public EmailData(String id_commercial, String email, Date date, String heure, boolean is_sender, boolean is_receiver) {
		super();
		this.id_commercial = id_commercial;
		this.email = email;
		this.date = date;
		this.heure = heure;
		this.is_sender = is_sender;
		this.is_receiver = is_receiver;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdCommercial() {
		return this.id_commercial;
	}

	public void setIdCommercial(String id_commercial) {
		this.id_commercial = id_commercial;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getHeure() {
		return this.heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}
	
	public boolean getIsSender() {
		return this.is_sender;
	}

	public void setIsSender(boolean is_sender) {
		this.is_sender = is_sender;
	}

	public boolean getIsReceiver() {
		return this.is_receiver;
	}

	public void setIsReceiver(boolean is_receiver) {
		this.is_receiver = is_receiver;
	}

	
	
}