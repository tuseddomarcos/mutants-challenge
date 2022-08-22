package com.mutant.recruter.repository;

import org.springframework.data.repository.CrudRepository;

import com.mutant.recruter.entitie.DnaStatistics;

public interface DnaStatisticsRepository extends CrudRepository<DnaStatistics, Integer> {
	
	public Long countByIsMutant(boolean isMutant); 

}
