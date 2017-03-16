package de.rwth.i9.cimt.service.kpextraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.algorithm.kpextraction.jate.Jate;
import de.rwth.i9.cimt.model.Keyword;
import uk.ac.shef.dcs.jate.JATEException;
import uk.ac.shef.dcs.jate.model.JATETerm;

@Service("jateKPExtraction")
public class JATEKPExtraction {
	private static final Logger log = LoggerFactory.getLogger(JATEKPExtraction.class);

	public List<Keyword> extractKeyword(String textbody, String algorithmName, int numKeywords) {
		List<Keyword> keywords = new ArrayList<Keyword>();
		int keywordCount = 0;
		try {
			List<JATETerm> terms = new ArrayList<JATETerm>();
			switch (algorithmName) {
			case "TTF":
				terms = Jate.TTFAlgo(textbody);
				break;

			case "ATTF":
				terms = Jate.ATTFAlgo(textbody);
				break;

			case "TFIDF":
				terms = Jate.TFIDFAlgo(textbody);
				break;

			case "RIDF":
				terms = Jate.RIDFAlgo(textbody);
				break;

			case "CValue":
				terms = Jate.CValueAlgo(textbody);
				break;

			case "ChiSquare":
				terms = Jate.ChiSquareAlgo(textbody);
				break;

			case "RAKE":
				terms = Jate.RAKEAlgo(textbody);
				break;

			case "Weirdness":
				terms = Jate.WeirdnessAlgo(textbody);
				break;

			case "GlossEx":
				terms = Jate.GlossExAlgo(textbody);
				break;

			case "TermEx":
				terms = Jate.TermExAlgo(textbody);
				break;
			default:
				break;
			}
			for (JATETerm term : terms) {
				Keyword keyword = new Keyword();
				keyword.setKeyword(term.getString());
				keyword.setScore(term.getScore());
				keywords.add(keyword);
				if (++keywordCount > numKeywords)
					break;
			}

		} catch (JATEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("JATE KeyphraseExtraction");
		return keywords;

	}
}
