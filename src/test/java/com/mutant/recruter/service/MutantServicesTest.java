package com.mutant.recruter.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.mutant.recruter.exception.InvalidDNAException;
import com.mutant.recruter.request.RequestMutant;

@ExtendWith(SpringExtension.class)
class MutantServicesTest {

	
	@Mock 
	private DnaStatisticsService dnaStatisticsService;

	@InjectMocks
	MutantServices mutantService;
	
	String[] mutantDna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
	String[] notMutantDna = {"GGGGCC","ATATAT","GCGCGC","ATATAT","GCGCGC","ATATAT"};
	String[] invalidDna = {"A"};

	RequestMutant adn;
 	
	@BeforeEach
	 void setUp() {
		ReflectionTestUtils.setField(mutantService, "dnaStatisticsService", dnaStatisticsService);
		adn = new RequestMutant(mutantDna);
	}
	
	@Test
	 void test_is_mutant() throws Exception {
		doNothing().when(dnaStatisticsService).updateStatistics(any());
		Assert.assertTrue(mutantService.isMutant(adn));
	}
	
	@Test
	 void test_is_not_mutant() throws Exception {
		doNothing().when(dnaStatisticsService).updateStatistics(any());
		adn = new RequestMutant(notMutantDna);
		Assert.assertFalse(mutantService.isMutant(adn));
	}
	
	@Test
	 void test_save_fails_with_invalid_dna() {
		adn = new RequestMutant(invalidDna);
		assertThrows(InvalidDNAException.class, () -> mutantService.isMutant(adn));
	}
}
