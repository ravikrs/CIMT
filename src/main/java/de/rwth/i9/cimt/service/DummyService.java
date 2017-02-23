package de.rwth.i9.cimt.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.service.nlp.opennlp.OpenNLPImpl;
import opennlp.tools.parser.Parse;

@Service("dummyService")
public class DummyService {
	private static final Logger logger = LoggerFactory.getLogger(DummyService.class);
	@Autowired
	OpenNLPImpl openNLPImpl;
	String sentence = "Who is the author of The Call of the Wild?";

	Set<String> nounPhrases = new HashSet<>();

	public void test() {
		Parse parse = openNLPImpl.parseSentence(sentence);
		System.out.println(Arrays.toString(openNLPImpl.tagPartOfSpeech(openNLPImpl.tokenize(sentence))));
		this.getNounPhrases(parse);
		nounPhrases.forEach(x -> System.out.println(x));
	}

	// recursively loop through tree, extracting noun phrases
	public void getNounPhrases(Parse p) {

		if (p.getType().equals("NP")) { // NP=noun phrase
			nounPhrases.add(p.getCoveredText());
		}
		for (Parse child : p.getChildren())
			getNounPhrases(child);
	}
}
