package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CommercialData;

@Repository
public interface CommercialDataRepository extends JpaRepository<CommercialData, Long> {

	@Query(value="SELECT commercial_data FROM CommercialData commercial_data WHERE commercial_data.is_delegated=true")
	public List<CommercialData> findCommercialWithDelegation();
	
	@Query("SELECT commercial_data FROM CommercialData commercial_data ORDER BY id ASC")
	public List<CommercialData> findAllCommercialData();
	
	
}
