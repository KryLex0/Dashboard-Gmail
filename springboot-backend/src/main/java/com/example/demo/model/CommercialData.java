package com.example.demo.model;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commercial_data")  //definition d'un nom de table
public class CommercialData{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_commercial") //definition d'un nom de colonne
	private String id_commercial;
	
	@Column(name = "nom_commercial") //definition d'un nom de colonne
	private String nom_commercial;
	
	@Column(name = "email_commercial") //definition d'un nom de colonne
	private String email_commercial;
	
	@Column(name = "nb_mails_received") //definition d'un nom de colonne
	private Long nb_mails_received;
	
	@Column(name = "nb_mails_sent") //definition d'un nom de colonne
	private Long nb_mails_sent;
	
	@Column(name = "total_mails") //definition d'un nom de colonne
	private Long total_mails;
	
	@Column(name = "is_delegated") //definition d'un nom de colonne
	private boolean is_delegated;


	public CommercialData() {}

	public CommercialData(CommercialData commercialData) {
		super();
		this.id_commercial = commercialData.getIdCommercial();
		this.nom_commercial = commercialData.getNomCommercial();
		this.email_commercial = commercialData.getEmailCommercial();
		this.nb_mails_received = commercialData.getNbMailsReceived();
		this.nb_mails_sent = commercialData.getNbMailsSent();
		this.total_mails = commercialData.getTotalMails();
		this.is_delegated = commercialData.getIsDelegated();
	}
	
	public CommercialData(String id_commercial, String nom_commercial, String email_commercial, Long nb_mails_received, Long nb_mails_sent, Long total_mails, boolean is_delegated) {
		super();
		this.id_commercial = id_commercial;
		this.nom_commercial = nom_commercial;
		this.email_commercial = email_commercial;
		this.nb_mails_received = nb_mails_received;
		this.nb_mails_sent = nb_mails_sent;
		this.total_mails = total_mails;
		this.is_delegated = is_delegated;
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
	
	public String getNomCommercial() {
		return this.nom_commercial;
	}

	public void setNomCommercial(String nom_commercial) {
		this.nom_commercial = nom_commercial;
	}
	
	public String getEmailCommercial() {
		return this.email_commercial;
	}

	public void setEmailCommercial(String email_commercial) {
		this.email_commercial = email_commercial;
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
	
	public Long getTotalMails() {
		return this.total_mails;
	}

	public void setTotalMails(Long total_mails) {
		this.total_mails = total_mails;
	}

	public boolean getIsDelegated() {
		return this.is_delegated;
	}

	public void setIsDelegated(boolean is_delegated) {
		this.is_delegated = is_delegated;
	}
	
	
	
}