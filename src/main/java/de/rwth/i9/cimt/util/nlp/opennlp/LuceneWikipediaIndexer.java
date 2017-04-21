package de.rwth.i9.cimt.util.nlp.opennlp;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.jwpl.WikipediaReaderBase;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants.Language;
import dkpro.similarity.uima.vsm.esaindexer.ExtendedWikipediaArticleReader;
import dkpro.similarity.uima.vsm.esaindexer.IndexInverter;
import dkpro.similarity.uima.vsm.esaindexer.LuceneIndexer;

public class LuceneWikipediaIndexer {

	private final static String luceneIndexPath = "target/lucene";
	private final static String esaIndexPath = "target/esa";

	public static void main(String[] args) throws Exception {
		createLuceneWikipediaIndex("en");
		createInvertedIndex();
		System.out.println("DONE");
		System.exit(0);
	}

	/**
	 * Creates a Lucene index from Wikipedia based on lower cased stems with
	 * length >=3 containing only characters.
	 * 
	 * @throws UIMAException
	 * @throws IOException
	 */
	private static void createLuceneWikipediaIndex(String language) throws UIMAException, IOException {
		CollectionReader reader = createReader(ExtendedWikipediaArticleReader.class, WikipediaReaderBase.PARAM_HOST,
				"localhost", WikipediaReaderBase.PARAM_DB, "simplewikidb", WikipediaReaderBase.PARAM_USER, "wikiuser",
				WikipediaReaderBase.PARAM_PASSWORD, "wikiuser", WikipediaReaderBase.PARAM_LANGUAGE,
				Language.simple_english);

		AnalysisEngine segmenter = createEngine(BreakIteratorSegmenter.class, BreakIteratorSegmenter.PARAM_LANGUAGE,
				language);

		AnalysisEngine stemmer = createEngine(SnowballStemmer.class, SnowballStemmer.PARAM_LANGUAGE, language,
				SnowballStemmer.PARAM_LOWER_CASE, true);

		AnalysisEngine indexTermGenerator = createEngine(LuceneIndexer.class, LuceneIndexer.PARAM_INDEX_PATH,
				luceneIndexPath, LuceneIndexer.PARAM_MIN_TERMS_PER_DOCUMENT, 50);

		SimplePipeline.runPipeline(reader, segmenter, stemmer, indexTermGenerator);

	}

	/**
	 * Creates an inverted index for ESA
	 * 
	 * @throws Exception
	 */
	private static void createInvertedIndex() throws Exception {
		IndexInverter indexInverter = new IndexInverter(new File(luceneIndexPath), new File(esaIndexPath));
		indexInverter.setMinDocumentFrequency(1);
		indexInverter.createInvertedIndex();
	}
}
