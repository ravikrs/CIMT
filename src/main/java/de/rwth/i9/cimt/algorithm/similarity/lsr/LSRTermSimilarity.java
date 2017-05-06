package de.rwth.i9.cimt.algorithm.similarity.lsr;

import java.util.Collection;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.tudarmstadt.ukp.dkpro.lexsemresource.LexicalSemanticResource;
import de.tudarmstadt.ukp.dkpro.lexsemresource.exception.LexicalSemanticResourceException;
import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.lsr.LexSemResourceComparator;
import dkpro.similarity.algorithms.lsr.gloss.GlossOverlapComparator;
import dkpro.similarity.algorithms.lsr.path.JiangConrathComparator;
import dkpro.similarity.algorithms.lsr.path.LeacockChodorowComparator;
import dkpro.similarity.algorithms.lsr.path.LinComparator;
import dkpro.similarity.algorithms.lsr.path.PathLengthComparator;
import dkpro.similarity.algorithms.lsr.path.ResnikComparator;
import dkpro.similarity.algorithms.lsr.path.WuPalmerComparator;

public class LSRTermSimilarity {
	private static final Logger log = LoggerFactory.getLogger(LSRTermSimilarity.class);
	private LexicalSemanticResource lsr;
	private LSRSimilarityAlgorithm lsrSimilarityAlgorithm;
	private LexSemResourceComparator similarityMeasure;

	public enum LSRSimilarityAlgorithm {
		GLOSSOVERLAP, JIANGCONRATH, LEACOCKCHODOROW, LIN, PATHLENGTH, RESNIK, WUPALMER;
		public static LSRSimilarityAlgorithm fromString(String value) {
			if ("GLOSSOVERLAP".equalsIgnoreCase(value))
				return GLOSSOVERLAP;
			if ("JIANGCONRATH".equalsIgnoreCase(value))
				return JIANGCONRATH;
			if ("LEACOCKCHODOROW".equalsIgnoreCase(value))
				return LEACOCKCHODOROW;
			if ("LIN".equalsIgnoreCase(value))
				return LIN;
			if ("PATHLENGTH".equalsIgnoreCase(value))
				return PATHLENGTH;
			if ("RESNIK".equalsIgnoreCase(value))
				return RESNIK;
			if ("WUPALMER".equalsIgnoreCase(value))
				return WUPALMER;
			if ("DEFAULT".equalsIgnoreCase(value))
				return JIANGCONRATH;
			return null;
		}
	}

	public LSRTermSimilarity(LexicalSemanticResource lsr, LSRSimilarityAlgorithm lsrSimilarityAlgorithm) {
		this.lsr = lsr;
		this.lsrSimilarityAlgorithm = lsrSimilarityAlgorithm;
		this.similarityMeasure = getLSRSimilarityMeasure(this.lsrSimilarityAlgorithm);

	}

	public double calculateLSRSimilarityMeasureWordNet(String token1, String token2) {
		double relatednessScore = 0.0;
		try {
			relatednessScore = similarityMeasure.getSimilarity(token1, token2);
		} catch (SimilarityException e) {
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

	private LexSemResourceComparator getLSRSimilarityMeasure(LSRSimilarityAlgorithm lsrSimilarityAlgorithm) {
		LexSemResourceComparator sm = null;
		try {
			switch (lsrSimilarityAlgorithm) {
			case GLOSSOVERLAP:
				sm = new GlossOverlapComparator(lsr, false);
				break;

			case JIANGCONRATH:
				sm = new JiangConrathComparator(lsr);
				break;

			case LEACOCKCHODOROW:
				sm = new LeacockChodorowComparator(lsr);
				break;

			case LIN:
				sm = new LinComparator(lsr);
				break;

			case PATHLENGTH:
				sm = new PathLengthComparator(lsr);
				break;

			case RESNIK:
				sm = new ResnikComparator(lsr);
				break;

			case WUPALMER:
				sm = new WuPalmerComparator(lsr);
				break;
			}
		} catch (LexicalSemanticResourceException lsrException) {
			log.error(ExceptionUtils.getStackTrace(lsrException));

		}
		return sm;

	}

}
