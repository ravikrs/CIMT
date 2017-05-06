package de.rwth.i9.cimt.service.similarity.vsm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.similarity.vsm.VSMTermSimilarity;
import de.rwth.i9.cimt.algorithm.similarity.vsm.VSMTermSimilarity.VSMSimilarityAlgorithm;
import de.rwth.i9.cimt.constants.SenseInventory;
import de.rwth.i9.cimt.service.similarity.SimilarityRelatednessService;
import de.rwth.i9.cimt.util.ScoringUtil;
import de.rwth.i9.cimt.util.ScoringUtil.ScoringStrategy;

@Service("wikipediaVSMSimilarityService")
public class WikipediaVSMSimilarityService implements SimilarityRelatednessService {
	private static final Logger log = LoggerFactory.getLogger(WikipediaVSMSimilarityService.class);
	private @Value("${cimt.home}") String cimtHome;

	@Override
	public double computeVectorRelatedness(List<String> vector1, List<String> vector2, String algorithmName) {
		double averageScore = 0.0;
		VSMTermSimilarity sm = new VSMTermSimilarity(SenseInventory.WIKIPEDIA, cimtHome + "ESA/LuceneIndexes/Wikipedia",
				VSMSimilarityAlgorithm.fromString(algorithmName));
		List<Double> relatednessValues = new ArrayList<>();
		for (String token1 : vector1) {
			for (String token2 : vector2) {
				relatednessValues.add(sm.calculateVSMSimilarityMeasure(token1, token2));
			}
		}
		averageScore = ScoringUtil.getScore(ScoringStrategy.AVERAGE, relatednessValues);
		return averageScore;
	}

	@Override
	public List<List<Double>> computeWordRelatedness(List<String> vector1, List<String> vector2, String algorithmName) {
		List<List<Double>> score = new ArrayList<>();
		List<Double> rowScore;
		VSMTermSimilarity sm = new VSMTermSimilarity(SenseInventory.WIKIPEDIA, cimtHome + "ESA/LuceneIndexes/Wikipedia",
				VSMSimilarityAlgorithm.fromString(algorithmName));
		for (String token1 : vector1) {
			rowScore = new ArrayList<>();
			for (String token2 : vector2) {
				rowScore.add(sm.calculateVSMSimilarityMeasure(token1, token2));
			}
			score.add(rowScore);
		}
		return score;

	}

}
