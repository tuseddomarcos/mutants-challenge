package com.mutant.recruter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mutant.recruter.dto.Stats;
import com.mutant.recruter.entitie.DnaStatistics;
import com.mutant.recruter.repository.DnaStatisticsRepository;

@Service
public class DnaStatisticsService {

	@Autowired
	DnaStatisticsRepository dnaStatisticsRepository;
	
	public void updateStatistics( DnaStatistics mutantDna) throws Exception {
			dnaStatisticsRepository.save(mutantDna);
	}
	
	public Stats getStats() {
		Long mutantCount = dnaStatisticsRepository.countByIsMutant(true);
		Long humanCount = dnaStatisticsRepository.countByIsMutant(false);
		return new Stats(mutantCount, humanCount);
	}
}
