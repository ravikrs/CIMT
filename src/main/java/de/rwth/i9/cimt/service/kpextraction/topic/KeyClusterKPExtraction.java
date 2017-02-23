package de.rwth.i9.cimt.service.kpextraction.topic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.model.Keyword;
import de.rwth.i9.cimt.service.kpextraction.RAKEKPExtraction;
import de.rwth.i9.cimt.service.nlp.opennlp.OpenNLPImpl;

@Service("keyClusterKPExtraction")
public class KeyClusterKPExtraction {

	private static final Logger log = LoggerFactory.getLogger(RAKEKPExtraction.class);

	@Autowired
	OpenNLPImpl openNLPImpl;

	public List<Keyword> extractKeyword(String text, int numKeywords) {
		return new ArrayList<Keyword>();
	}

}
