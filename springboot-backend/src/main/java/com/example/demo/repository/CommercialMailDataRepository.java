package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CommercialData;
import com.example.demo.model.CommercialMailData;

@Repository
public interface CommercialMailDataRepository extends JpaRepository<CommercialMailData, Long> {
	
	@Query("SELECT commercial_mail_data FROM CommercialMailData commercial_mail_data ORDER BY date_start_week")
	public List<CommercialMailData> findCommercialMailDataOrderByDateStartWeek();
	
	@Query("SELECT commercial_mail_data FROM CommercialMailData commercial_mail_data WHERE commercial_mail_data.id_commercial = :idCommercial")
	public List<CommercialMailData> findByIdCommercial(@Param("idCommercial") String idCommercial);
	
	/*
	@Query("SELECT commercial_mail_data FROM CommercialMailData commercial_mail_data WHERE commercial_mail_data.id_commercial = :id_commercial, commercial_mail_data.date_start_week = :date_start_week, commercial_mail_data.date_end_week = :date_end_week")
	public CommercialMailData findCommercialMailDataByDate(
			@Param("id_commercial") String idCommercial,
			@Param("date_start_week") java.sql.Date dateD,
			@Param("date_end_week") java.sql.Date dateF);
	*/
	
}
