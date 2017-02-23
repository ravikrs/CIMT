package de.rwth.i9.cimt.algorithm.kpextraction.rake;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.service.nlp.opennlp.OpenNLPImpl;
import edu.ehu.galan.rake.RakeAlgorithm;
import edu.ehu.galan.rake.model.Document;
import edu.ehu.galan.rake.model.Term;
import edu.ehu.galan.rake.model.Token;

public class Rake {
	private static final Logger log = LoggerFactory.getLogger(Rake.class);
	private static int id = 0;

	public static List<Keyword> extractKeyword(String text, OpenNLPImpl openNlpImpl) {
		List<LinkedList<Token>> tokenizedSentenceList = new ArrayList<LinkedList<Token>>();
		List<String> sentenceList = new ArrayList<String>();
		LinkedList<Token> tokenList = null;
		List<Keyword> keywords = new ArrayList<Keyword>();

		for (String sentence : openNlpImpl.detectSentences(text)) {
			sentenceList.add(sentence);
			String[] tokens = openNlpImpl.tokenize(sentence);
			String[] posTags = openNlpImpl.tagPartOfSpeech(tokens);
			tokenList = new LinkedList<Token>();
			for (int i = 0; i < tokens.length; i++) {
				tokenList.add(new Token(tokens[i], posTags[i]));
			}

		}

		Document doc = new Document("", "");
		doc.setSentenceList(sentenceList);
		doc.List(tokenizedSentenceList);
		RakeAlgorithm ex = new RakeAlgorithm();
		ex.loadStopWordsList("src/main/resources/stopLists/SmartStopListEn");
		ex.loadPunctStopWord("src/main/resources/stopLists/RakePunctDefaultStopList");
		ex.init(doc, "");
		ex.runAlgorithm();
		List<Term> terms = doc.getTermList();
		for (Term term : terms) {
			Keyword keyword = new Keyword();
			keyword.setKeyword(term.getTerm());
			keyword.setScore(term.getScore());
			keywords.add(keyword);
		}
		return keywords;
	}
}
