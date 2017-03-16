package de.rwth.i9.cimt.service.kpextraction.graph;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharethis.textrank.LanguageEnglish;
import com.sharethis.textrank.TextRank;
import com.sharethis.textrank.TextRankWordnet;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.service.nlp.opennlp.OpenNLPImpl;

@Service("textRankKPExtraction")
public class TextRankKPExtraction {
	private static final Logger log = LoggerFactory.getLogger(TextRankKPExtraction.class);
	@Autowired
	OpenNLPImpl openNLPImpl;
	@Autowired
	LanguageEnglish languageEnglish;

	public List<Keyword> extractKeywordTextRank(String text, int numKeyword) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		List<Keyword> totalkeywords = TextRank.extractKeywordTextRank(text, numKeyword, openNLPImpl, languageEnglish);
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
		List<Keyword> keywords = new ArrayList<Keyword>();
		List<Keyword> totalkeywords = TextRankWordnet.extractKeywordTextRankWordnet(text, numKeyword, openNLPImpl,
				languageEnglish);
		for (Keyword keyword : totalkeywords) {
			if (keywords.size() >= numKeyword) {
				break;
			}
			keywords.add(keyword);
		}
		return keywords;
	}

}
