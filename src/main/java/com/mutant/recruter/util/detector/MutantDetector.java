package com.mutant.recruter.util.detector;

import com.mutant.recruter.constants.Constants;
import com.mutant.recruter.dto.RequestMutant;
import com.mutant.recruter.exception.InvalidDNAException;

public class MutantDetector {
	

	private String[] dna;
	private int foundSequences;
	private int consecutive;

	public MutantDetector(RequestMutant dna) {
		this.dna = dna.getDna();
	}

	public boolean isMutant() {

		foundSequences = 0;
		validateTableSize();

		readRows();
		readColumns();
		readUpFowardDiagonals();
		readDownFowardDiagonals();

		return foundSequences >= Constants.SEQUENCES_FOR_POSITIVE;
	}

	private void readRows() {
		for (int i = 0; i < dna.length; i++) {
			validateRow(i);
			validateCharAt(i,0);
			consecutive = 0;
			for (int j = 0; j < dna.length - 1; j++) {
				validateCharAt(i, j + 1);
				readPosition(i, j, i, j + 1);
			}
		}
	}

	private void readColumns() {
		int j = 0;
		while (keepChecking() && j < dna.length) {
			int i = 0;
			consecutive = 0;
			while (keepChecking() && enoughRemainingSpace(dna.length - i)) {
				readPosition(i, j, i + 1, j);
				i++;
			}
			j++;
		}
	}

	private void readUpFowardDiagonals() {
		int row = Constants.SEQUENCE_LENGTH - 1;
		while (keepChecking() && row < dna.length) {
			int i = row;
			int j = 0;
			while (keepChecking() && enoughRemainingSpace((row + 1) - j)) {
				readPosition(i, j, i - 1, j + 1);
				i--;
				j++;
			}
			row++;
		}

		int column = 1;

		while (keepChecking() && column < (dna.length - Constants.SEQUENCE_LENGTH + 1)) {

			int i = dna.length - 1;
			int j = column;

			while (keepChecking() && enoughRemainingSpace(dna.length - j)) {
				readPosition(i, j, i - 1, j + 1);
				i--;
				j++;
			}
			column++;
		}

	}
	private void readDownFowardDiagonals() {
		int row = dna.length - Constants.SEQUENCE_LENGTH;
		while (keepChecking() && row >= 0) {
			int i = row;
			int j = 0;
			
			while (keepChecking() && enoughRemainingSpace((dna.length - row) - j)) {
				readPosition(i, j, i + 1, j + 1);
				i++;
				j++;
			}
			row--;
		}
		int column = 1;

		while (keepChecking() && column < (dna.length - Constants.SEQUENCE_LENGTH + 1)) {

			int i = 0;
			int j = column;
			
			while (keepChecking() && enoughRemainingSpace((dna.length - column) - i)) {
				readPosition(i, j, i + 1, j + 1);
				i++;
				j++;
			}
			column++;
		}
	}
	private void readPosition(int i, int j, int nextI, int nextJ) {

		char currentChar = getCharAt(i, j);
		char nextChar = getCharAt(nextI, nextJ);

		if (currentChar == nextChar) {
			consecutive++;
		} else {
			consecutive = 0;
		}

		if (consecutive == Constants.SEQUENCE_LENGTH - 1) {
			foundSequences++;
			consecutive = 0;
		}
	}

	private boolean keepChecking() {
		return foundSequences < Constants.SEQUENCES_FOR_POSITIVE;
	}
	
	private boolean enoughRemainingSpace(int remainingSpace) {
		return remainingSpace >= Constants.SEQUENCE_LENGTH - consecutive ;
	}

	private char getCharAt(int i, int j) {
		return dna[i].charAt(j);
	}

	private void validateCharAt(int i, int j) {
		char c = getCharAt(i, j);
		if (!Constants.POSSIBLE_LETTERS.contains(c)) {
			throw new InvalidDNAException("Invalid Character '" + c + "' at position (" + j + "," + i + ").");
		}
	}

	private void validateRow(int i) {
		if (dna[i].length() != dna.length) {
			throw new InvalidDNAException("Row " + i + " does not match dna array lenght. Table sould be NxN.");
		}
	}

	private void validateTableSize() {
		if (dna.length < Constants.SEQUENCE_LENGTH) {
			throw new InvalidDNAException("Row does not match dna array lenght");
		}
	}

}
