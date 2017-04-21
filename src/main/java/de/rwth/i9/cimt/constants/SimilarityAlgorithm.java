package de.rwth.i9.cimt.constants;

public enum SimilarityAlgorithm {

	DUMMY, 
	OTHER;
	
	public static SimilarityAlgorithm fromString(String value) {
		if ("DUMMY".equalsIgnoreCase(value))
			return DUMMY;
		return OTHER;
	}
}
