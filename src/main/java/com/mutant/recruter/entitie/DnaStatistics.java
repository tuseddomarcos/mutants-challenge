package com.mutant.recruter.entitie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DNA_STATISTICS")
public class DnaStatistics {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name= "id")
	private int id;
	
	@Column(name= "dnaMutant")
	private String dna;
	
	@Column(name= "isMutant")
	private boolean isMutant;
	
	public DnaStatistics(String dna, boolean mutant) {
		this.dna = dna;
		this.isMutant = mutant;
	}

	public DnaStatistics() {}

	public String getDna() {
		return dna;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}

	public boolean isMutant() {
		return isMutant;
	}

	public void setMutant(boolean isMutant) {
		this.isMutant = isMutant;
	}
	
	
}
