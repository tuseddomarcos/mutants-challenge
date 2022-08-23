package com.mutant.recruter.util.detector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mutant.recruter.dto.RequestMutant;
import com.mutant.recruter.exception.InvalidDNAException;

class MutantDetectorTest {
	

	@Test
	void test_is_mutant() {
		String[] dnaInput = { "TAAAAT", "GGCCCC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertTrue(new MutantDetector(dna).isMutant());
	}

	@Test
	void test_is_not_mutant() {
		String[] dnaInput = { "GGGGCC", "AAATAT", "GCGCGC", "ATATAT", "GCGCGC", "ATATAT" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertFalse(new MutantDetector(dna).isMutant());
	}
	
	
	@Test
	 void test_is_not_mutant_with_two_sequences_of_trhee() {
		String[] dnaInput = { "TAAATT", "GAGCCC", "TTATGT", "AGAAGG", "TCACTG", "TCACTG" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertFalse(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_mutant_with_one_horizontal_and_one_vertical_sequence() {
		String[] dnaInput = { "ATATAT", "GCGCGT", "ATATAT", "ACGCGT", "ATATAT", "ACGCGC" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertTrue(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_mutant_with_two_diagonals() {
		String[] dnaInput = { "ATGTGA", "CATTGC", "TTATTT", "TGAAGG", "GCGTCA", "TCACTG" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertTrue(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_not_mutant_with_only_one_diagonal() {
		String[] dnaInput = { "ATGGGA", "CATTGC", "TTATTT", "TGAAGG", "GCGTCA", "TCACTG" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertFalse(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_mutant_with_two_diagonals_inside() {
		String[] dnaInput = { "GTGTGA", "CATTGC", "TTATTT", "TGAAGG", "GCGTAA", "TCACTG" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertTrue(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_not_mutant_with_only_one_horizontal_five_letter_sequence() {
		String[] dnaInput = { "ATATAT", "GGGGGC", "ATATAT", "GCGCGC", "ATATAT", "GCGCGC" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertFalse(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_not_mutant_with_only_one_vertical_five_letter_sequence() {
		String[] dnaInput = { "ATATAT", "ACGCGC", "ATATAT", "ACGCGC", "ATATAT", "GCGCGC" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertFalse(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_not_mutant_with_only_one_foward_diagonal_five_letter_sequence() {
		String[] dnaInput = { "ATATAT", "GAGCGC", "ATATAT", "GCGAGC", "ATATAT", "GCGCGC" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertFalse(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_not_mutant_with_only_one_backward_diagonal_five_letter_sequence() {
		String[] dnaInput = { "ATATAT", "GCGCTC", "ATATAT", "GCTCGC", "ATATAT", "GCGCGC" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertFalse(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_is_mutant_with_one_sequence_of_five_and_one_sequence_of_four() {
		String[] dnaInput = { "AAAAAT", "GCGCGC", "GTATAT", "GCGCGC", "GTATAT", "CCGCGC" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertTrue(new MutantDetector(dna).isMutant());
	}

	@Test
	 void test_invalid_table_size() {
		String[] dnaInput = { "AAA", "GCG", "GTA" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertThrows(InvalidDNAException.class, () -> new MutantDetector(dna).isMutant());
	}

	 void test_invalid_row_length() {
		String rowOfFour = "AAAA";
		String[] dnaInput = { "AAAAAT", rowOfFour, "GTATAT", "GCGCGC", "GTATAT", "CCGCGC" };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertThrows(InvalidDNAException.class, () -> new MutantDetector(dna).isMutant());
	}

	@Test()
	 void test_invalid_character() {
		char x = 'X';
		String[] dnaInput = { "AAAAAT", "GCGCGG", "GTATAT", "GCGCGC", "GTATAT", ("CCGCG" + x) };
		RequestMutant dna = new RequestMutant(dnaInput);
		assertThrows(InvalidDNAException.class, () -> new MutantDetector(dna).isMutant());
	}

}
