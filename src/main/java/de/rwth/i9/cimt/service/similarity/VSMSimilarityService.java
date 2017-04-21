package de.rwth.i9.cimt.service.similarity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.similarity.vsm.VSMTermSimilarity;

@Service("vsmSimilarityService")
public class VSMSimilarityService implements SimilarityRelatednessService {
	private static final Logger log = LoggerFactory.getLogger(VSMSimilarityService.class);

	@Override
	public double computeVectorRelatedness(List<String> vector1, List<String> vector2) {
		double averageScore = 0.0;
		double sum = 0.0;
		VSMTermSimilarity sm = new VSMTermSimilarity();
		List<Double> relatednessValues = new ArrayList<>();
		for (String token1 : vector1) {
			for (String token2 : vector2) {
				relatednessValues.add(sm.calculateVSMSimilarityMeasure(token1, token2));
			}
		}
		if (!relatednessValues.isEmpty()) {
			for (Double relatednessScore : relatednessValues) {
				sum += relatednessScore.doubleValue();
			}
			averageScore = sum / relatednessValues.size();
		}

		return averageScore;
	}

	@Override
	public List<List<Double>> computeWordRelatedness(List<String> vector1, List<String> vector2) {
		List<List<Double>> score = new ArrayList<>();
		List<Double> rowScore;
		VSMTermSimilarity sm = new VSMTermSimilarity();
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
