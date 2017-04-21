package de.rwth.i9.cimt.util.nlp.opennlp;

import java.io.IOException;
import java.io.InputStream;

import de.rwth.i9.cimt.constants.nlp.NlpModelType;
import opennlp.tools.lemmatizer.SimpleLemmatizer;

public class OpenNlpModelManager {

	private static final String MODEL_PATH = "/";
	private static final String EN_CHUNKER_MODEL = MODEL_PATH + "en-chunker.bin";
	private static final String EN_SENTDEC_MODEL = MODEL_PATH + "en-sent.bin";
	private static final String EN_PARSER_MODEL = MODEL_PATH + "en-parser-chunking.bin";
	private static final String EN_POSTAG_MODEL = MODEL_PATH + "en-pos-maxent.bin";
	private static final String EN_TOKENIZER_MODEL = MODEL_PATH + "en-token.bin";

	private static final String DE_CHUNKER_MODEL = MODEL_PATH + "en-chunker.bin";
	private static final String DE_SENTDEC_MODEL = MODEL_PATH + "en-sent.bin";
	private static final String DE_PARSER_MODEL = MODEL_PATH + "en-parser-chunking.bin";
	private static final String DE_POSTAG_MODEL = MODEL_PATH + "en-pos-maxent.bin";
	private static final String DE_TOKENIZER_MODEL = MODEL_PATH + "en-token.bin";

	/**
	 * Method to retrieve input stream to appropriate model for NLP task type
	 * and language. Returns default model if it there is no trained model
	 * present.
	 * 
	 * @param lang
	 * @param nlpTaskType
	 * @return
	 */
	public InputStream retrieveModelAsStream(String lang, NlpModelType nlpTaskType) {
		return null;
	}

	private static SimpleLemmatizer lemmatizer;

	private String lemmatize(String word, String postag) throws IOException {
		if (lemmatizer == null) {
			InputStream is = getClass().getResourceAsStream("/models/en-lemmatizer.dict");
			lemmatizer = new SimpleLemmatizer(is);
			is.close();
		}
		String lemma = lemmatizer.lemmatize(word, postag);
		return lemma;
	}

}
