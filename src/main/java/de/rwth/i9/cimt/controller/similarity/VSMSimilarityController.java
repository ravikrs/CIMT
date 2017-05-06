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
@RequestMapping("/similarity/vsm")
public class VSMSimilarityController {

	private static final Logger log = LoggerFactory.getLogger(VSMSimilarityController.class);

	@Autowired
	SimilarityRelatednessService wikipediaVSMSimilarityService;
	@Autowired
	SimilarityRelatednessService wktnlVSMSimilarityService;
	@Autowired
	SimilarityRelatednessService wnVSMSimilarityService;

	@RequestMapping(value = "/wordnet", method = RequestMethod.GET)
	public double computeRelatednessScoreTokensWordnet(@RequestParam("token1") String token1,
			@RequestParam("token2") String token2) {
		return wnVSMSimilarityService.computeVectorRelatedness(Arrays.asList(token1), Arrays.asList(token2), "default");
	}

	@RequestMapping(value = "/wordnet", method = RequestMethod.POST)
	public double computeRelatednessScoreListWordnet(@RequestBody SimilarityVector sv) {
		return wnVSMSimilarityService.computeVectorRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wordnet/vector", method = RequestMethod.POST)
	public List<List<Double>> computeRelatednessScoreWordWordnet(@RequestBody SimilarityVector sv) {
		return wnVSMSimilarityService.computeWordRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wiktionary", method = RequestMethod.GET)
	public double computeRelatednessScoreTokensWiktionary(@RequestParam("token1") String token1,
			@RequestParam("token2") String token2) {
		return wktnlVSMSimilarityService.computeVectorRelatedness(Arrays.asList(token1), Arrays.asList(token2),
				"default");
	}

	@RequestMapping(value = "/wiktionary", method = RequestMethod.POST)
	public double computeRelatednessScoreListWiktionary(@RequestBody SimilarityVector sv) {
		return wktnlVSMSimilarityService.computeVectorRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wiktionary/vector", method = RequestMethod.POST)
	public List<List<Double>> computeRelatednessScoreWordWiktionary(@RequestBody SimilarityVector sv) {
		return wktnlVSMSimilarityService.computeWordRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wikipedia", method = RequestMethod.GET)
	public double computeRelatednessScoreTokensWikipedia(@RequestParam("token1") String token1,
			@RequestParam("token2") String token2) {
		return wikipediaVSMSimilarityService.computeVectorRelatedness(Arrays.asList(token1), Arrays.asList(token2),
				"default");
	}

	@RequestMapping(value = "/wikipedia", method = RequestMethod.POST)
	public double computeRelatednessScoreListWikipedia(@RequestBody SimilarityVector sv) {
		return wikipediaVSMSimilarityService.computeVectorRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

	@RequestMapping(value = "/wikipedia/vector", method = RequestMethod.POST)
	public List<List<Double>> computeRelatednessScoreWordWikipedia(@RequestBody SimilarityVector sv) {
		return wikipediaVSMSimilarityService.computeWordRelatedness(sv.getVector1(), sv.getVector2(),
				sv.getSimilarityAlgorithm());
	}

}
