package de.rwth.i9.cimt.controller.similarity;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.rwth.i9.cimt.model.SimilarityVector;
import de.rwth.i9.cimt.service.similarity.SimilarityRelatednessService;

@RestController
@RequestMapping("/similarity/lsr")
public class LSRSimilarityController {

	private static final Logger log = LoggerFactory.getLogger(LSRSimilarityController.class);

	@Autowired
	SimilarityRelatednessService wnLSRSimilarityService;

	@Autowired
	SimilarityRelatednessService wktnlLSRSimilarityService;

	@RequestMapping(value = "/wordnet", method = RequestMethod.GET)
	public double computeRelatednessScoreTokensWordNet(@RequestParam("token1") String token1,
			@RequestParam("token2") String token2) {
		return wnLSRSimilarityService.computeVectorRelatedness(Arrays.asList(token1), Arrays.asList(token2), "default");
	}

	@RequestMapping(value = "/wordnet", method = RequestMethod.POST)
	public double computeRelatednessScoreListWordNet(@RequestBody SimilarityVector sv) {
		return wnLSRSimilarityService.computeVectorRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wordnet/vector", method = RequestMethod.POST)
	public List<List<Double>> computeRelatednessScoreWordWordNet(@RequestBody SimilarityVector sv) {
		return wnLSRSimilarityService.computeWordRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wiktionary", method = RequestMethod.GET)
	public double computeRelatednessScoreTokensWiktionary(@RequestParam("token1") String token1,
			@RequestParam("token2") String token2) {
		return wktnlLSRSimilarityService.computeVectorRelatedness(Arrays.asList(token1), Arrays.asList(token2),
				"default");
	}

	@RequestMapping(value = "/wiktionary", method = RequestMethod.POST)
	public double computeRelatednessScoreListWiktionary(@RequestBody SimilarityVector sv) {
		return wktnlLSRSimilarityService.computeVectorRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wiktionary/vector", method = RequestMethod.POST)
	public List<List<Double>> computeRelatednessScoreWordWiktionary(@RequestBody SimilarityVector sv) {
		return wktnlLSRSimilarityService.computeWordRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

}
