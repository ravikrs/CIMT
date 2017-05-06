package de.rwth.i9.cimt.service.similarity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.similarity.wikipedia.WikipediaTermSimilarity;
import de.rwth.i9.cimt.algorithm.similarity.wikipedia.WikipediaTermSimilarity.WikipediaSimilarityAlgorithm;
import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;

@Service("wikipediaSimilarityService")
public class WikipediaSimilarityService implements SimilarityRelatednessService {
	private static final Logger log = LoggerFactory.getLogger(WikipediaSimilarityService.class);
	@Autowired
	@Lazy
	Wikipedia wikipedia;

	@Override
	public double computeVectorRelatedness(List<String> vector1, List<String> vector2, String algorithmName) {
		double relatednessScore = 0.0;
		WikipediaTermSimilarity sm = new WikipediaTermSimilarity(wikipedia,
				WikipediaSimilarityAlgorithm.fromString(algorithmName));
		relatednessScore = sm.calculatesWikipediaBasedMeasure(vector1, vector2);
		return relatednessScore;
	}

	@Override
	public List<List<Double>> computeWordRelatedness(List<String> vector1, List<String> vector2, String algorithmName) {
		List<List<Double>> score = new ArrayList<>();
		List<Double> rowScore;
		WikipediaTermSimilarity sm = new WikipediaTermSimilarity(wikipedia,
				WikipediaSimilarityAlgorithm.fromString(algorithmName));
		for (String token1 : vector1) {
			rowScore = new ArrayList<>();
			for (String token2 : vector2) {
				rowScore.add(sm.calculatesWikipediaBasedMeasure(token1, token2));
			}
			score.add(rowScore);
		}
		return score;
	}

}
