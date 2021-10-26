package com.example.demo.model;

import java.sql.Date;


import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commercial_mail_data")  //definition d'un nom de table
public class CommercialMailData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_commercial") //definition d'un nom de colonne
	private String id_commercial;
	
	@Column(name = "date_start_week") //definition d'un nom de colonne
	private Date date_start_week;
	
	@Column(name = "date_end_week") //definition d'un nom de colonne
	private Date date_end_week;
	
	@Column(name = "nb_mails_received") //definition d'un nom de colonne
	private Long nb_mails_received;
	
	@Column(name = "nb_mails_sent") //definition d'un nom de colonne
	private Long nb_mails_sent;



	public CommercialMailData() {}
	
	public CommercialMailData(String id_commercial, Date date_start_week, Date date_end_week, Long nb_mails_received, Long nb_mails_sent) {
		super();
		this.id_commercial = id_commercial;
		this.date_start_week = date_start_week;
		this.date_end_week= date_end_week;
		this.nb_mails_received = nb_mails_received;
		this.nb_mails_sent = nb_mails_sent;
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
	
	public Date getDateStartWeek() {
		return this.date_start_week;
	}

	public void setDateStartWeek(Date date_start_week) {
		this.date_start_week = date_start_week;
	}
	
	public Date getDateEndWeek() {
		return this.date_end_week;
	}

	public void setDateEndWeek(Date date_end_week) {
		this.date_end_week = date_end_week;
	}
	
	public Long getNbMailsReceived() {
		return this.nb_mails_received;
	}

	public void setNbMailsReceived(Long nb_mails_received) {
		this.nb_mails_received = nb_mails_received;
	}
	
	public Long getNbMailsSent() {
		return this.nb_mails_sent;
	}

	public void setNbMailsSent(Long nb_mails_sent) {
		this.nb_mails_sent = nb_mails_sent;
	}
	
	
}