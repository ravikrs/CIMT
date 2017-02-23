package de.rwth.i9.cimt.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.rwth.i9.cimt.service.nlp.NLP;

@Component
public class NlpImplFactory {
	@Autowired
	private NLP openNLPImpl;

	public NLP getNlpImplementation(String nlpImplName) {
		if ("openNLP".equalsIgnoreCase(nlpImplName)) {
			return openNLPImpl;
		}
		return null;
	}

}
