package de.rwth.i9.cimt.util;

import java.util.List;

public class ScoringUtil {
	public enum ScoringStrategy {
		AVERAGE, BEST, WORST
	};

	public static double getScore(ScoringStrategy scoringStrategy, List<Double> values) {
		double score = 0.0;
		if (values.isEmpty()) {
			return score;
		}
		switch (scoringStrategy) {
		case AVERAGE:
			double sum = 0.0;
			for (double val : values) {
				sum += val;
			}
			score = sum / values.size();
			break;

		default:
			break;
		}
		return score;

	}

}
