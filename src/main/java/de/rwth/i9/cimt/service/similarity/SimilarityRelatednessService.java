package de.rwth.i9.cimt.service.similarity;

import java.util.List;

public interface SimilarityRelatednessService {
	public double computeVectorRelatedness(List<String> vector1, List<String> vector2);

	public List<List<Double>> computeWordRelatedness(List<String> vector1, List<String> vector2);
}
