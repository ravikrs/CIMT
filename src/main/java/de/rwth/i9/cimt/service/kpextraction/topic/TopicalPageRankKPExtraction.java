package de.rwth.i9.cimt.service.kpextraction.topic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.kpextraction.unsupervised.graphranking.TextRank;
import de.rwth.i9.cimt.algorithm.kpextraction.unsupervised.topicclustering.TopicalPageRank;
import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.service.nlp.NLP;

@Service("topicalPageRankKPExtraction")
public class TopicalPageRankKPExtraction {
	private static final Logger logger = LoggerFactory.getLogger(TopicalPageRankKPExtraction.class);
	@Autowired
	NLP nlpImpl;

	public List<Keyword> extractKeywordTR(String textbody, int numKeywords) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		int iter = 0;
		for (Keyword keyword : TextRank.performTextRankKE(textbody, nlpImpl)) {
			if (iter == numKeywords) {
				break;
			}
			keywords.add(keyword);
		}
		return keywords;
	}

	public List<Keyword> extractKeywordTPR(String textbody, int numKeywords) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		int iter = 0;
		for (Keyword keyword : TopicalPageRank.performTopicalPageRankKE(textbody, nlpImpl)) {
			if (iter == numKeywords) {
				break;
			}
			keywords.add(keyword);
		}
		return keywords;
	}

}
