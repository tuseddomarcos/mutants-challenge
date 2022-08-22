package com.mutant.recruter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutant.recruter.dto.Stats;
import com.mutant.recruter.request.RequestMutant;
import com.mutant.recruter.service.DnaStatisticsService;
import com.mutant.recruter.service.MutantServices;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MutantController.class)
class MutantControllerTest {
	
	@MockBean 
	MutantServices  mutantService;
	@MockBean
	DnaStatisticsService dnaStatisticsService; 
	private AutoCloseable closeable;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	MutantController mutantController;

	RequestMutant adn;
	
	String[] mutantDna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
	String[] notMutantDna = {"GGGGCC","ATATAT","GCGCGC","ATATAT","GCGCGC","ATATAT"};

 	

	@BeforeEach
	 void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		
		adn = new RequestMutant(mutantDna);
	}
	
	 @AfterEach
	    void closeService() throws Exception {
	        closeable.close();
	    }
	
	@Test
	 void test_is_mutant() throws Exception {
		doReturn(true).when(mutantService).isMutant(any());
		mockMvc.perform(post("/mutant/").content(asJsonString(new RequestMutant(mutantDna)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("true"));
		
	}
	
	@Test
	 void test_is_not_mutant() throws Exception {
		doReturn(false).when(mutantService).isMutant(any());
		mockMvc.perform(post("/mutant/").content(asJsonString(new RequestMutant(notMutantDna)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden()).andExpect(content().string("false"));
		
	}
	
	@Test
	 void test_stats() throws Exception {
		Stats stats = new Stats(Long.valueOf("40"), Long.valueOf("100"));
		doReturn(stats).when(dnaStatisticsService).getStats();
		mockMvc.perform(get("/stats")).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.count_human_dna").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.ratio").exists());
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
