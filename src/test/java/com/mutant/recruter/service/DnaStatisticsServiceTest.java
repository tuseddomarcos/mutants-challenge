package com.mutant.recruter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.mutant.recruter.dto.Stats;
import com.mutant.recruter.repository.DnaStatisticsRepository;

@ExtendWith(SpringExtension.class)
class DnaStatisticsServiceTest {
	
	@InjectMocks
	DnaStatisticsService dnaStatisticsService;
	
	@Mock
	DnaStatisticsRepository dnaStatisticsRepository;
	

	@BeforeEach
	 void setUp() {
		ReflectionTestUtils.setField(dnaStatisticsService, "dnaStatisticsRepository", dnaStatisticsRepository);
	}
	
	@Test
	public void test_stats_ratio() {
		doReturn((long) 50).when(dnaStatisticsRepository).countByIsMutant(true);
		doReturn((long) 100).when(dnaStatisticsRepository).countByIsMutant(false);
		Stats stats = dnaStatisticsService.getStats();
		assertEquals(0.5, stats.getRatio());	}
}
