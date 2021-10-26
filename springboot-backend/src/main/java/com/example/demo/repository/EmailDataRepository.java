package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.EmailData;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
	
	@Query("SELECT email_data FROM EmailData email_data WHERE email_data.is_receiver=true AND email_data.is_sender=false AND email_data.id_commercial=:idCommercial ORDER BY date DESC, heure DESC")
	public List<EmailData> findEmailDataIsReceiver(@Param("idCommercial") String idCommercial);
	
	@Query("SELECT email_data FROM EmailData email_data WHERE email_data.is_receiver=false AND email_data.is_sender=true AND email_data.id_commercial=:idCommercial ORDER BY date DESC, heure DESC")
	public List<EmailData> findEmailDataIsSender(@Param("idCommercial") String idCommercial);
	
	@Query("SELECT email_data FROM EmailData email_data WHERE email_data.id_commercial=:idCommercial ORDER BY date DESC, heure DESC")
	public List<EmailData> findEmailDataByIdCommercial(@Param("idCommercial") String idCommercial);
	
	
}
