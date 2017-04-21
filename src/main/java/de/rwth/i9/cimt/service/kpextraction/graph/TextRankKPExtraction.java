package de.rwth.i9.cimt.service.kpextraction.graph;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.kpextraction.textrank.LanguageEnglish;
import de.rwth.i9.cimt.algorithm.kpextraction.textrank.TextRankWordnet;
import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.service.nlp.opennlp.OpenNLPImpl;

@Service("textRankKPExtraction")
public class TextRankKPExtraction {
	private static final Logger log = LoggerFactory.getLogger(TextRankKPExtraction.class);
	@Autowired
	OpenNLPImpl openNLPImpl;
	@Autowired
	LanguageEnglish languageEnglish;
	private @Value("${cimt.en.wn}") String wordNetPath;
	private @Value("${cimt.home}") String cimtHome;

	public List<Keyword> extractKeywordTextRank(String text, int numKeyword) {
		List<Keyword> keywords = new ArrayList<>();
		List<Keyword> totalkeywords = TextRankWordnet.extractKeywordTextRankWordnet(text, openNLPImpl, languageEnglish,
				cimtHome + "src/main/resources/en/wordnet3.0", false);
		totalkeywords.sort(Keyword.KeywordComparatorDesc);
		for (Keyword keyword : totalkeywords) {
			if (keywords.size() >= numKeyword) {
				break;
			}
			keywords.add(keyword);
		}
		log.info("TextRank KeyphraseExtraction");
		return keywords;
	}

	public List<Keyword> extractKeywordTextRankWordnet(String text, int numKeyword) {
		List<Keyword> keywords = new ArrayList<>();
		List<Keyword> totalkeywords = TextRankWordnet.extractKeywordTextRankWordnet(text, openNLPImpl, languageEnglish,
				cimtHome + "src/main/resources/en/wordnet3.0", true);
		totalkeywords.sort(Keyword.KeywordComparatorDesc);
		for (Keyword keyword : totalkeywords) {
			if (keywords.size() >= numKeyword) {
				break;
			}
			keywords.add(keyword);
		}
		return keywords;
	}

}
