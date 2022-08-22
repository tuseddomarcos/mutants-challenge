package com.mutant.recruter.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mutant.recruter.entitie.DnaStatistics;
import com.mutant.recruter.request.RequestMutant;
import com.mutant.recruter.util.detector.MutantDetector;

@Service
public class MutantServices {
	protected Logger logger = LogManager.getLogger(getClass());
	
	@Autowired
	private DnaStatisticsService dnaStatisticsService;
	
	public boolean isMutant(RequestMutant dna) throws Exception {
		logger.info("Validate if dna is mutant");
			boolean validateMutant = new MutantDetector(dna).isMutant();
			DnaStatistics mutantDna = new DnaStatistics(dna.getDna(),validateMutant);
			dnaStatisticsService.updateStatistics(mutantDna);
			return validateMutant;
		
		
	}

}
