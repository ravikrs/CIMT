package de.rwth.i9.cimt.algorithm.similarity.lsr;

import java.util.Collection;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.tudarmstadt.ukp.dkpro.lexsemresource.LexicalSemanticResource;
import de.tudarmstadt.ukp.dkpro.lexsemresource.exception.LexicalSemanticResourceException;
import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.lsr.LexSemResourceComparator;
import dkpro.similarity.algorithms.lsr.path.PathLengthComparator;

public class LSRTermSimilarity {
	private static final Logger log = LoggerFactory.getLogger(LSRTermSimilarity.class);
	private LexicalSemanticResource lsr;

	public LSRTermSimilarity(LexicalSemanticResource lsr) {
		this.lsr = lsr;
	}

	public double calculateLSRSimilarityMeasureWordNet(String token1, String token2) {
		double relatednessScore = 0.0;
		try {
			LexSemResourceComparator comparator = new PathLengthComparator(lsr);
			relatednessScore = comparator.getSimilarity(token1, token2);
		} catch (LexicalSemanticResourceException | SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return relatednessScore;
	}

	public double calculateLSRSimilarityMeasureWordNet(Collection<String> c1, Collection<String> c2) {
		double relatednessScore = 0.0;
		try {
			LexSemResourceComparator comparator = new PathLengthComparator(lsr);
			relatednessScore = comparator.getSimilarity(c1, c2);
		} catch (LexicalSemanticResourceException | SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return relatednessScore;
	}

}
