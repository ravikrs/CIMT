package de.rwth.i9.cimt.algorithm.similarity.sspace;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.api.TextSimilarityMeasure;

public class SSTermSimilarity {
	private static final Logger log = LoggerFactory.getLogger(SSTermSimilarity.class);

	public double calculateLSRSimilarityMeasureWordNet(String token1, String token2) {
		double relatednessScore = 0.0;
		try {
			TextSimilarityMeasure lsa = new LsaSimilarityMeasure(new File("src/test/resources/model/test.sspace"));
			relatednessScore = lsa.getSimilarity(token1, token2);
		} catch (IOException | SimilarityException e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return relatednessScore;
	}

	public double calculateLSRSimilarityMeasureWordNet(Collection<String> c1, Collection<String> c2) {
		double relatednessScore = 0.0;
		try {
			TextSimilarityMeasure lsa = new LsaSimilarityMeasure(new File("src/test/resources/model/test.sspace"));
			relatednessScore = lsa.getSimilarity(c1, c2);
		} catch (IOException | SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return relatednessScore;
	}

}
