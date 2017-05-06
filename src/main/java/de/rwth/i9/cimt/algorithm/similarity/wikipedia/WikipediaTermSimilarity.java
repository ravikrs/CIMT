package de.rwth.i9.cimt.algorithm.similarity.wikipedia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rwth.i9.cimt.util.ScoringUtil;
import de.rwth.i9.cimt.util.ScoringUtil.ScoringStrategy;
import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;
import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.wikipedia.WikipediaBasedComparator;
import dkpro.similarity.algorithms.wikipedia.WikipediaBasedComparator.WikipediaBasedRelatednessMeasure;
import dkpro.similarity.algorithms.wikipedia.measures.WikiLinkComparator;

public class WikipediaTermSimilarity {
	private static final Logger log = LoggerFactory.getLogger(WikipediaTermSimilarity.class);

	public enum WikipediaSimilarityAlgorithm {
		WIKIPEDIALINKMEASURE, JIANGCONRATH, LEACOCKCHODOROW, LESKFIRST, LESKFULL, LIN, PATHLENGTH, RESNIK, WUPALMER;
		public static WikipediaSimilarityAlgorithm fromString(String value) {
			if ("WIKIPEDIALINKMEASURE".equalsIgnoreCase(value))
				return WIKIPEDIALINKMEASURE;
			if ("JIANGCONRATH".equalsIgnoreCase(value))
				return JIANGCONRATH;
			if ("LEACOCKCHODOROW".equalsIgnoreCase(value))
				return LEACOCKCHODOROW;
			if ("LESKFIRST".equalsIgnoreCase(value))
				return LESKFIRST;
			if ("LESKFULL".equalsIgnoreCase(value))
				return LESKFULL;
			if ("LIN".equalsIgnoreCase(value))
				return LIN;
			if ("PATHLENGTH".equalsIgnoreCase(value))
				return PATHLENGTH;
			if ("RESNIK".equalsIgnoreCase(value))
				return RESNIK;
			if ("WUPALMER".equalsIgnoreCase(value))
				return WUPALMER;
			if ("DEFAULT".equalsIgnoreCase(value))
				return WIKIPEDIALINKMEASURE;

			return null;
		}
	}

	private Wikipedia wiki;
	private WikipediaSimilarityAlgorithm similarityAlgorithm;
	private WikipediaBasedComparator similarityMeasure;
	private WikiLinkComparator wlc;

	public WikipediaTermSimilarity(Wikipedia wiki, WikipediaSimilarityAlgorithm algorithmName) {
		this.wiki = wiki;
		this.similarityAlgorithm = algorithmName;
		if (this.similarityAlgorithm.equals(WikipediaSimilarityAlgorithm.WIKIPEDIALINKMEASURE)) {
			wlc = new WikiLinkComparator(this.wiki, true);
		}
		this.similarityMeasure = getsimilarityComparator(similarityAlgorithm);
	}

	public double calculatesWikipediaBasedMeasure(String token1, String token2) {
		double relatednessScore = 0;
		try {
			if (similarityAlgorithm.equals(WikipediaSimilarityAlgorithm.WIKIPEDIALINKMEASURE)) {
				relatednessScore = wlc.getSimilarity(token1, token2);
			} else {
				// calculate similarity
				relatednessScore = similarityMeasure.getSimilarity(token1, token2);
			}
		} catch (SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}

		return relatednessScore;
	}

	private WikipediaBasedComparator getsimilarityComparator(WikipediaSimilarityAlgorithm similarityAlgorithm) {
		WikipediaBasedComparator sm = null;
		try {
			switch (similarityAlgorithm) {
			case JIANGCONRATH:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.JiangConrath, false);
				break;

			case LEACOCKCHODOROW:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.LeacockChodorow, false);
				break;

			case LESKFIRST:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.LeskFirst, false);
				break;

			case LESKFULL:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.LeskFull, false);
				break;

			case LIN:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.Lin, false);
				break;

			case PATHLENGTH:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.PathLength, false);
				break;

			case RESNIK:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.Resnik, false);
				break;
			case WUPALMER:
				sm = new WikipediaBasedComparator(wiki, WikipediaBasedRelatednessMeasure.WuPalmer, false);
				break;

			}
		} catch (SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return sm;

	}

	public double calculatesWikipediaBasedMeasure(Collection<String> c1, Collection<String> c2) {
		double relatednessScore = 0;
		List<Double> relatednessValues = new ArrayList<>();
		for (String t1 : c1) {
			for (String t2 : c2) {
				relatednessValues.add(calculatesWikipediaBasedMeasure(t1, t2));
			}
		}
		relatednessScore = ScoringUtil.getScore(ScoringStrategy.AVERAGE, relatednessValues);
		return relatednessScore;
	}
}
