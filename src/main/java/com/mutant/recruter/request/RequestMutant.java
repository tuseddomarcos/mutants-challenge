package com.mutant.recruter.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class RequestMutant implements Serializable {
	
	private static final long serialVersionUID = 7018455321833419322L;
	
	@JsonProperty("dna")
	private String [] dna;
	
	
	public RequestMutant(String[] dnaInput) {
		this.dna = dnaInput;
	}


	public RequestMutant() {
	}


	public String[] getDna() {
		return this.dna;
	}
}