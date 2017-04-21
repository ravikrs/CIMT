package de.rwth.i9.cimt.algorithm.similarity.wikipedia;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;
import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.wikipedia.WikipediaBasedComparator;
import dkpro.similarity.algorithms.wikipedia.WikipediaBasedComparator.WikipediaBasedRelatednessMeasure;
import dkpro.similarity.algorithms.wikipedia.measures.WikiLinkComparator;

public class WikipediaTermSimilarity {
	private static final Logger log = LoggerFactory.getLogger(WikipediaTermSimilarity.class);

	private Wikipedia wiki;

	public WikipediaTermSimilarity(Wikipedia wiki) {
		this.wiki = wiki;
	}

	public double calculateWikipediaLinkMeasure(String token1, String token2) {
		double relatednessScore = 0;
		WikiLinkComparator wlc = new WikiLinkComparator(wiki, true);
		try {
			relatednessScore = wlc.getSimilarity(token1, token2);
		} catch (SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			return 0;
		}
		return relatednessScore;
	}

	public double calculatesWikipediaBasedMeasure(WikipediaBasedRelatednessMeasure aMeasure, String token1,
			String token2) {
		double relatednessScore = 0;
		try {
			WikipediaBasedComparator sm = new WikipediaBasedComparator(wiki, aMeasure, false);
			relatednessScore = sm.getSimilarity(token1, token2);
		} catch (SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
		}
		return relatednessScore;
	}

}
