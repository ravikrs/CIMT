package de.rwth.i9.cimt.service.kpextraction;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.service.nlp.opennlp.OpenNLPImpl;
import de.rwth.i9.cimt.test.AppRake;

@Service("rakeKPExtraction")
public class RAKEKPExtraction {
	private static final Logger logger = LoggerFactory.getLogger(RAKEKPExtraction.class);

	@Autowired
	OpenNLPImpl openNLPImpl;

	public List<Keyword> extractKeywordRAKE(String text, int numKeywords) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		List<Keyword> totalKeywords = new ArrayList<Keyword>();
		int keywordCount = 0;
		totalKeywords = AppRake.rakeAlgo(text, openNLPImpl);
		for (Keyword keyword : totalKeywords) {
			keywords.add(keyword);
			if (++keywordCount > numKeywords)
				break;
		}

		return keywords;
	}

}
