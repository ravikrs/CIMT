package de.rwth.i9.cimt.service.similarity;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.similarity.lsr.CimtWordNetResource;
import de.rwth.i9.cimt.algorithm.similarity.lsr.LSRTermSimilarity;

@Service("lsrSimilarityService")
public class LSRSimilarityService implements SimilarityRelatednessService {
	private static final Logger log = LoggerFactory.getLogger(LSRSimilarityService.class);
	@Autowired
	CimtWordNetResource wordNetResource;
	private @Value("${cimt.home}") String cimtHome;

	@Override
	public double computeVectorRelatedness(List<String> vector1, List<String> vector2) {
		double relatednessScore = 0.0;
		LSRTermSimilarity sm = new LSRTermSimilarity(wordNetResource);
		relatednessScore = sm.calculateLSRSimilarityMeasureWordNet(vector1, vector2);

		return relatednessScore;
	}

	@Override
	public List<List<Double>> computeWordRelatedness(List<String> vector1, List<String> vector2) {
		List<List<Double>> score = new ArrayList<>();
		List<Double> rowScore;
		LSRTermSimilarity sm = new LSRTermSimilarity(wordNetResource);
		for (String token1 : vector1) {
			rowScore = new ArrayList<>();
			for (String token2 : vector2) {
				rowScore.add(sm.calculateLSRSimilarityMeasureWordNet(token1, token2));
			}
			score.add(rowScore);
		}
		return score;

	}

}
