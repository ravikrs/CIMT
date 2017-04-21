package de.rwth.i9.cimt.service.similarity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.similarity.wikipedia.WikipediaTermSimilarity;
import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;

@Service("wikipediaSimilarityService")
public class WikipediaSimilarityService implements SimilarityRelatednessService {
	private static final Logger log = LoggerFactory.getLogger(WikipediaSimilarityService.class);
	@Autowired
	Wikipedia wikipedia;

	@Override
	public double computeVectorRelatedness(List<String> vector1, List<String> vector2) {
		double relatednessScore = 0.0;
		// TODO add wikihost object
		WikipediaTermSimilarity sm = new WikipediaTermSimilarity(wikipedia);
		return relatednessScore;
	}

	@Override
	public List<List<Double>> computeWordRelatedness(List<String> vector1, List<String> vector2) {
		List<List<Double>> score = new ArrayList<>();
		List<Double> rowScore;
		WikipediaTermSimilarity sm = new WikipediaTermSimilarity(wikipedia);
		for (String token1 : vector1) {
			rowScore = new ArrayList<>();
			for (String token2 : vector2) {
				rowScore.add(sm.calculateWikipediaLinkMeasure(token1, token2));
			}
			score.add(rowScore);
		}
		return score;

	}

}
